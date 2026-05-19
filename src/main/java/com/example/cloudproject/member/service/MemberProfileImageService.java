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

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberProfileImageService {

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional
    public void uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException( "ID "+ memberId + " 는 존재하지 않는 항목입니다.")
        );

        String oldKey = member.getProfileImageKey();

        String newKey = s3Service.uploadProfileImage(memberId, file);

        member.updateProfileImageKey(newKey);

        if (oldKey != null && !oldKey.isBlank()) {
            s3Service.deleteFile(oldKey);
        }
    }

    @Transactional(readOnly = true)
    public GetProfileImageUrlResponseDto getProfileImageUrl(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException( "ID "+ memberId + " 는 존재하지 않는 항목입니다.")
        );

        String key = member.getProfileImageKey();

        if (key == null || key.isBlank()) {
            throw new NotFoundException("등록된 프로필 이미지가 없습니다.");
        }

        String downloadUrl = s3Service.createDownloadUrl(key);

        return GetProfileImageUrlResponseDto.from(downloadUrl);
    }


}
