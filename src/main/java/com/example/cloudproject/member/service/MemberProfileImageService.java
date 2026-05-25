package com.example.cloudproject.member.service;

import com.example.cloudproject.global.exception.NotFoundException;
import com.example.cloudproject.global.s3.S3Service;
import com.example.cloudproject.member.dto.response.GetProfileImageUrlResponseDto;
import com.example.cloudproject.member.entity.Member;
import com.example.cloudproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 프로필 이미지 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberProfileImageService {

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    /**
     * 프로필 이미지 교체 및 기존 이미지 정리
     *
     * @param memberId 팀원 ID
     * @param file 프로필 이미지 파일
     */
    @Transactional
    public void uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("ID " + memberId + " 는 존재하지 않는 항목입니다.")
        );

        String oldKey = member.getProfileImageKey();

        String newKey = s3Service.uploadProfileImage(memberId, file);

        member.updateProfileImageKey(newKey);

        log.info("[BUSINESS EVENT] 프로필 이미지 Key 갱신 memberId={} key={}", memberId, newKey);

        if (oldKey != null && !oldKey.isBlank()) {
            s3Service.deleteFile(oldKey);
            log.info("[BUSINESS EVENT] 기존 프로필 이미지 삭제 memberId={} oldKey={}", memberId, oldKey);
        }
    }

    /**
     * 프로필 이미지 조회 URL 응답 생성
     *
     * @param memberId 팀원 ID
     * @return 프로필 이미지 URL 응답
     */
    @Transactional(readOnly = true)
    public GetProfileImageUrlResponseDto getProfileImageUrl(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("ID " + memberId + " 는 존재하지 않는 항목입니다.")
        );

        String key = member.getProfileImageKey();

        if (key == null || key.isBlank()) {
            throw new NotFoundException("등록된 프로필 이미지가 없습니다.");
        }

        String downloadUrl = s3Service.createDownloadUrl(key);

        return GetProfileImageUrlResponseDto.from(downloadUrl);
    }
}