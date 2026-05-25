package com.example.cloudproject.global.s3;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * AWS S3 클라이언트 설정
 */
@Configuration
public class S3Config {

    @Value("${cloud.aws.region}")
    private String region;

    /**
     * S3 클라이언트 Bean
     *
     * @return S3 클라이언트
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }

    /**
     * S3 Presigner Bean
     *
     * @return S3 Presigner
     */
    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of(region))
                .build();
    }


}
