package com.example.cloudproject.member.dto.response;

import lombok.Getter;

/**
 * 프로필 이미지 URL 응답 DTO
 */
@Getter
public class GetProfileImageUrlResponseDto {

    private final String profileImageUrl;

    /**
     * 프로필 이미지 URL 응답 생성자
     *
     * @param profileImageUrl 프로필 이미지 URL
     */
    public GetProfileImageUrlResponseDto(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * 프로필 이미지 URL 응답 변환
     *
     * @param profileImageUrl 프로필 이미지 URL
     * @return 프로필 이미지 URL 응답
     */
    public static GetProfileImageUrlResponseDto from(String profileImageUrl) {
        return new GetProfileImageUrlResponseDto(profileImageUrl);
    }


}
