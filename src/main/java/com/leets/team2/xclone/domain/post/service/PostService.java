package com.leets.team2.xclone.domain.post.service;

import com.leets.team2.xclone.domain.member.entities.Member;
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
        Post post=Post.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .imageUrl(imageUrl)
                .author(author)
                .build();
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, PostRequestDTO postRequestDTO, MultipartFile imageFile, Member currentMember)throws IOException{
        Post post=postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        //현재 사용자가 게시물 작성자인지 확인
        if(!post.getAuthor().getId().equals(currentMember.getId())){
            throw new UnauthorizedException();
        }
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());

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
        postRepository.deleteById(postId);//해당 게시물 아이디의 게시물 삭제
    }

    public PostResponseDTO getPost(Long postId){
        Post post=postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        return new PostResponseDTO(post.getId(),post.getTitle(),post.getContent(), post.getImageUrl());
    }

    public List<PostResponseDTO> getAllPosts(){
        return postRepository.findAll().stream()
                .map(post->new PostResponseDTO(post.getId(),post.getTitle(),post.getContent(),post.getImageUrl()))
                .collect(Collectors.toList());
    }
}
