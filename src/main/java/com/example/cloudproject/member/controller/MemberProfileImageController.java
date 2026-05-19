package com.example.cloudproject.member.controller;


import com.example.cloudproject.member.dto.response.GetProfileImageUrlResponseDto;
import com.example.cloudproject.member.service.MemberProfileImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/profile-image")
public class MemberProfileImageController {

    private final MemberProfileImageService memberProfileImageService;

    //post
    @PostMapping
    public ResponseEntity<Void> uploadProfileImage(
            @PathVariable Long memberId,
            @RequestParam("file") MultipartFile file) {

        log.info("[API - LOG] 프로필 이미지 업로드 요청 ID = {}", memberId);

        memberProfileImageService.uploadProfileImage(memberId, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<GetProfileImageUrlResponseDto> getProfileImageUrl(
            @PathVariable Long memberId) {

        log.info("[API - LOG] 프로필 이미지 URL 조회 요청 ID = {}", memberId);

        return ResponseEntity.status(HttpStatus.OK).body(memberProfileImageService.getProfileImageUrl(memberId));

    }
}
