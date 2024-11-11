package com.leets.team2.xclone.domain.post.service;

import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.post.dto.PostEditRequestDTO;
import com.leets.team2.xclone.domain.post.dto.PostRequestDTO;
import com.leets.team2.xclone.domain.post.dto.PostResponseDTO;
import com.leets.team2.xclone.domain.post.dto.RepostResponseDTO;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.repository.PostRepository;
import com.leets.team2.xclone.exception.PostNotFoundException;
import com.leets.team2.xclone.exception.UnauthorizedException;
import com.leets.team2.xclone.image.service.ImageSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageSaveService imageSaveService;

    public Post createPost(PostRequestDTO postRequestDTO, Member author, List<MultipartFile> images) throws IOException {
        List<String> imageUrls;
        imageUrls = imageSaveService.uploadImages(images);


        Post parentPost=null;
        if(postRequestDTO.getParentPostId()!=null){
            parentPost=postRepository.findById(postRequestDTO.getParentPostId())
                    .orElseThrow(PostNotFoundException::new);
        }

        Post quotePost=null;
        if(postRequestDTO.getQuotePostId()!=null){
            quotePost=postRepository.findById(postRequestDTO.getQuotePostId())
                    .orElseThrow(PostNotFoundException::new);
        }

        Post post=Post.builder()
                .content(postRequestDTO.getContent())
                .imageUrls(imageUrls)
                .author(author)
                .parentPost(parentPost)
                .quotePost(quotePost)
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long postId, PostEditRequestDTO postEditRequestDTO, List<MultipartFile> images, Member currentMember)throws IOException{
        Post post=postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        //현재 사용자가 게시물 작성자인지 확인
        if(!post.getAuthor().getId().equals(currentMember.getId())){
            throw new UnauthorizedException();
        }
        post.setContent(postEditRequestDTO.getContent());

        if(!images.isEmpty()){//새로운 이미지가 있을 경우 원래 있던 이미지 삭제하고 업데이트한다.
            List<String> imageUrls;
            imageUrls = imageSaveService.uploadImages(images);
            post.setImageUrls(imageUrls);
        }
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId,Member currentMember){
        //게시물이 존재하지 않으면 예외발생. 작성자 정보에도 접근하기 위해 existsById에서 findById로 수정
        Post post=postRepository.findById(postId)
                        .orElseThrow(PostNotFoundException::new);

        //현재 사용자가 게시물 작성자인지 확인
        if(!post.getAuthor().getId().equals(currentMember.getId())){
            throw new UnauthorizedException();
        }

        List<Post> childPosts = post.getChildPosts();
        for (Post childPost : childPosts) {
            childPost.setParentPost(null);//부모와 자식간의 관계를 없앤다.
        }

        postRepository.deleteById(postId);//해당 게시물 아이디의 게시물 삭제
    }

    public PostResponseDTO getPost(Long postId){
        Post post=postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        return toPostResponseDTO(post);
    }

    public List<PostResponseDTO> getAllPosts(){
        List<Post>posts=postRepository.findAll()
                .stream()
                .filter(post -> post.getParentPost()==null)//댓글 게시물은 필터링
                .collect(Collectors.toList());
        return posts.stream()
                .map(this::toPostResponseDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO toPostResponseDTO(Post post){//자식 게시물 리스트를 DTO로 변환, 인용 게시물 DTO에 담기
        List<PostResponseDTO> childPosts = post.getChildPosts() != null ? post.getChildPosts().stream()
                .map(this::toPostResponseDTO)
                .collect(Collectors.toList()) : Collections.emptyList();//childPosts가 null이 되는 경우를 대비한다. null이면 빈 리스트로 처리.

        RepostResponseDTO repostResponseDTO=null;
        if(post.getQuotePost()!=null){
            repostResponseDTO=RepostResponseDTO.builder()
                    .id(post.getQuotePost().getId())
                    .authorNickname(post.getQuotePost().getAuthor().getNickname())
                    .authorTag(post.getQuotePost().getAuthor().getTag())
                    .content(post.getQuotePost().getContent())
                    .imageUrls(post.getQuotePost().getImageUrls())
                    .build();
        }//인용한 게시물 가져오기

        String authorNickname=post.getAuthor().getNickname();
        String authorTag=post.getAuthor().getTag();

        return new PostResponseDTO(
                post.getId(),
                authorNickname,
                authorTag,
                post.getContent(),
                post.getImageUrls(),
                post.getParentPost() != null ? post.getParentPost().getId() : null,
                repostResponseDTO,
                childPosts

        );
    }
}
