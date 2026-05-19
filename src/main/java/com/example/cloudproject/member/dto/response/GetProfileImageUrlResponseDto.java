package com.example.cloudproject.member.dto.response;

import lombok.Getter;

@Getter
public class GetProfileImageUrlResponseDto {

    private final String profileImageUrl;

    public GetProfileImageUrlResponseDto(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public static GetProfileImageUrlResponseDto from(String profileImageUrl) {
        return new GetProfileImageUrlResponseDto(profileImageUrl);
    }


}
