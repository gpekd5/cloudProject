package com.example.cloudproject.global.s3;

import com.example.cloudproject.global.exception.BadRequestException;
import com.example.cloudproject.global.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.util.UUID;

/**
 * 프로필 이미지 S3 저장소 연동 서비스
 */
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.cloudfront.domain}")
    private String cloudFrontDomain;

    /**
     * 프로필 이미지 검증 및 S3 업로드
     *
     * @param Id 팀원 ID
     * @param file 프로필 이미지 파일
     * @return S3 객체 키
     */
    public String uploadProfileImage(Long Id, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("업로드할 파일이 없습니다.");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("이미지 파일만 업로드할 수 있습니다.");
        }

        String originalFilename = file.getOriginalFilename();

        // 원래의 파일명이 null 값인지 확인
        // 공백이 있으면 _로 치환
        String safeFilename = (originalFilename == null || originalFilename.isBlank())
                ? "image.png" : originalFilename.replaceAll("\\s+", "_");

        String key = "uploads/profiles/" + Id + "/" + UUID.randomUUID() + "-" + safeFilename;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(contentType)
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            return key;

        } catch (IOException e) {
            throw new InternalServerException("S3 파일 업로드 중 오류가 발생했습니다.");
        }
    }

    /**
     * CloudFront 기반 프로필 이미지 URL 생성
     *
     * @param key S3 객체 키
     * @return CloudFront 이미지 URL
     */
    public String createDownloadUrl(String key) {
        if (key == null || key.isBlank()) {
            throw new BadRequestException("프로필 이미지 경로가 없습니다.");
        }

        String normalizedDomain = cloudFrontDomain.endsWith("/")
                ? cloudFrontDomain.substring(0, cloudFrontDomain.length() - 1)
                : cloudFrontDomain;


        return normalizedDomain + "/" + key;
    }

    /**
     * S3 객체 삭제 요청
     *
     * @param key S3 객체 키
     */
    public void deleteFile(String key) {
        if (key == null || key.isBlank()) {
            return;
        }

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

}
