package com.leets.team2.xclone.image.service;

import com.leets.team2.xclone.exception.UnsupportedFileFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageSaveService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    private static final String HTTPS_PROTOCOL = "https://";

    // 지원하는 이미지 파일 확장자 목록
    private static final String[] SUPPORTED_EXTENSIONS = { "jpg", "jpeg", "png" };

    public List<String> uploadImages(List<MultipartFile> images) {
        // 다중 업로드 && 리스트 ","을 기준으로 하나의 문자열 반환
        if(images == null || images.isEmpty())
            return List.of();   // images가 비었다면 빈 리스트 반환

        return images.parallelStream()
                .map(file -> {
                    java.io.File fileObj = convertMultiPartFileToFile(file);
                    String fileExtension = getFileExtension(file.getOriginalFilename());
                    if(!isSupportedFileExtension(fileExtension)){
                        throw new UnsupportedFileFormatException();
                    }
                    String fileName = generateUniqueFileName(file, fileExtension);
                    s3Client.putObject(new PutObjectRequest(bucket, fileName, fileObj));    // s3에 파일 업로드
                    fileObj.delete();   // 업로드 후 파일 삭제

                    return extractUrl(fileName);    // 파일명들 추출
                })
                .toList();
    }

    private boolean isSupportedFileExtension(String fileExtension){
        for(String ext:SUPPORTED_EXTENSIONS){
            if(ext.equalsIgnoreCase(fileExtension)){
                return true;
            }
        }
        return false;
    }



    private static String generateUniqueFileName(MultipartFile file, String fileExtension) {
        return UUID.randomUUID() + "." + fileExtension;
        // 고유한 파일 이름을 생성
    }

    private String extractUrl(String fileName) {
        // S3 URL을 한 번만 호출하여 결과를 변수에 저장
        java.net.URL s3Url = s3Client.getUrl(bucket, fileName);

        return HTTPS_PROTOCOL + s3Url.getHost() + s3Url.getFile();
    }

    private java.io.File convertMultiPartFileToFile(MultipartFile file) {
        // MultipartFile을 java.io.File로 변환
        java.io.File convertedFile = new java.io.File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    private static String getFileExtension(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }
}
