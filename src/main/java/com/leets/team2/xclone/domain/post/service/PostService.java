package com.leets.team2.xclone.domain.post.service;

import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.post.dto.PostEditRequestDTO;
import com.leets.team2.xclone.domain.post.dto.PostRequestDTO;
import com.leets.team2.xclone.domain.post.dto.PostResponseDTO;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.repository.PostRepository;
import com.leets.team2.xclone.exception.InvalidFileException;
import com.leets.team2.xclone.exception.PostNotFoundException;
import com.leets.team2.xclone.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(PostRequestDTO postRequestDTO, Member author, MultipartFile imageFile) throws IOException {
        String imageUrl=null;//이미지 url 초기화

        if(imageFile!=null&&!imageFile.isEmpty()){
            if(imageFile.getContentType()==null||!imageFile.getContentType().equals("image/png")){
                throw new InvalidFileException();
            }

            String imageName= UUID.randomUUID().toString()+"_"+imageFile.getOriginalFilename();//이미지 파일의 이름 생성
            Path imagePath= Paths.get("uploads/"+imageName);//저장 경로 설정
            Files.createDirectories(imagePath.getParent());//폴더 없으면 새로 생성
            Files.write(imagePath,imageFile.getBytes());//파일을 경로에 저장

            imageUrl=imagePath.toString();
        }

        Post parentPost=null;
        if(postRequestDTO.getParentPostId()!=null){
            parentPost=postRepository.findById(postRequestDTO.getParentPostId())
                    .orElseThrow(PostNotFoundException::new);
        }

        Post post=Post.builder()
                .content(postRequestDTO.getContent())
                .imageUrl(imageUrl)
                .author(author)
                .parentPost(parentPost)
                .build();
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, PostEditRequestDTO postEditRequestDTO, MultipartFile imageFile, Member currentMember)throws IOException{
        Post post=postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        //현재 사용자가 게시물 작성자인지 확인
        if(!post.getAuthor().getId().equals(currentMember.getId())){
            throw new UnauthorizedException();
        }
        post.setContent(postEditRequestDTO.getContent());

        if(imageFile!=null&&!imageFile.isEmpty()){//새로운 이미지가 있을 경우 원래 있던 이미지 삭제하고 업데이트한다.
            if (imageFile.getContentType()==null||!imageFile.getContentType().equals("image/png")) {
                throw new InvalidFileException();
            }
            if(post.getImageUrl()!=null){
                Path existingImagePath=Paths.get(post.getImageUrl());
                Files.deleteIfExists(existingImagePath);//기존 파일 삭제(존재한다면)
            }
            String imageName= UUID.randomUUID().toString()+"_"+imageFile.getOriginalFilename();//이미지 파일의 이름 생성
            Path imagePath= Paths.get("uploads/"+imageName);//저장 경로 설정
            Files.createDirectories(imagePath.getParent());//폴더 없으면 새로 생성
            Files.write(imagePath,imageFile.getBytes());//파일을 경로에 저장

            post.setImageUrl(imagePath.toString());
        }
        return postRepository.save(post);
    }

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

    public PostResponseDTO toPostResponseDTO(Post post){//자식 게시물 리스트를 DTO로 변환
        List<PostResponseDTO> childPosts = post.getChildPosts() != null ? post.getChildPosts().stream()
                .map(this::toPostResponseDTO)
                .collect(Collectors.toList()) : Collections.emptyList();//childPosts가 null이 되는 경우를 대비한다. null이면 빈 리스트로 처리.
        return new PostResponseDTO(
                post.getId(),
                post.getContent(),
                post.getImageUrl(),
                post.getParentPost() != null ? post.getParentPost().getId() : null,
                childPosts
        );
    }
}
