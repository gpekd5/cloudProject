package com.example.cloudproject.member.controller;

import com.example.cloudproject.member.dto.response.GetProfileImageUrlResponseDto;
import com.example.cloudproject.member.service.MemberProfileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 프로필 이미지 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/profile-image")
public class MemberProfileImageController {

    private final MemberProfileImageService memberProfileImageService;

    /**
     * 프로필 이미지 업로드
     *
     * @param memberId 팀원 ID
     * @param file 프로필 이미지 파일
     * @return 빈 응답
     */
    @PostMapping
    public ResponseEntity<Void> uploadProfileImage(
            @PathVariable Long memberId,
            @RequestParam("file") MultipartFile file
    ) {
        memberProfileImageService.uploadProfileImage(memberId, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 프로필 이미지 URL 조회
     *
     * @param memberId 팀원 ID
     * @return 프로필 이미지 URL 응답
     */
    @GetMapping
    public ResponseEntity<GetProfileImageUrlResponseDto> getProfileImageUrl(
            @PathVariable Long memberId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberProfileImageService.getProfileImageUrl(memberId));
    }
}