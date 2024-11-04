package com.leets.team2.xclone.domain.post.service;

import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.post.dto.PostDTO;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.repository.PostRepository;
import com.leets.team2.xclone.exception.InvalidFileException;
import com.leets.team2.xclone.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(PostDTO postDTO, Member author, MultipartFile imageFile) throws IOException {
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
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .imageUrl(imageUrl)
                .author(author)
                .build();
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, PostDTO postDTO, MultipartFile imageFile)throws IOException{
        Post post=postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

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

    public void deletePost(Long postId){
        if(!postRepository.existsById(postId)){//게시물이 없을 경우 예외 발생
            throw new PostNotFoundException();
        }
        postRepository.deleteById(postId);//해당 게시물 아이디의 게시물 삭제
    }
}
