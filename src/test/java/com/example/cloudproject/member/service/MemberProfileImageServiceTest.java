package com.example.cloudproject.member.service;

import com.example.cloudproject.global.exception.NotFoundException;
import com.example.cloudproject.global.s3.S3Service;
import com.example.cloudproject.member.dto.response.GetProfileImageUrlResponseDto;
import com.example.cloudproject.member.entity.Member;
import com.example.cloudproject.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberProfileImageServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private MemberProfileImageService memberProfileImageService;

    @Test
    @DisplayName("프로필 이미지 업로드 성공")
    void uploadProfileImage_success() {
        // given
        Long memberId = 1L;
        Member member = new Member("홍길동", 20, "INTJ");
        ReflectionTestUtils.setField(member, "id", memberId);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "profile.png",
                "image/png",
                "image-content".getBytes()
        );

        String newKey = "uploads/profiles/1/new-profile.png";

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(s3Service.uploadProfileImage(memberId, file)).thenReturn(newKey);

        // when
        memberProfileImageService.uploadProfileImage(memberId, file);

        // then
        assertThat(member.getProfileImageKey()).isEqualTo(newKey);

        verify(memberRepository, times(1)).findById(memberId);
        verify(s3Service, times(1)).uploadProfileImage(memberId, file);
        verify(s3Service, never()).deleteFile(anyString());
    }

    @Test
    @DisplayName("기존 프로필 이미지가 있으면 삭제 후 새 이미지로 변경")
    void uploadProfileImage_deleteOldImage() {
        // given
        Long memberId = 1L;
        Member member = new Member("홍길동", 20, "INTJ");
        ReflectionTestUtils.setField(member, "id", memberId);
        member.updateProfileImageKey("uploads/profiles/1/old-profile.png");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "profile.png",
                "image/png",
                "image-content".getBytes()
        );

        String newKey = "uploads/profiles/1/new-profile.png";

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(s3Service.uploadProfileImage(memberId, file)).thenReturn(newKey);

        // when
        memberProfileImageService.uploadProfileImage(memberId, file);

        // then
        assertThat(member.getProfileImageKey()).isEqualTo(newKey);

        verify(memberRepository, times(1)).findById(memberId);
        verify(s3Service, times(1)).uploadProfileImage(memberId, file);
        verify(s3Service, times(1)).deleteFile("uploads/profiles/1/old-profile.png");
    }

    @Test
    @DisplayName("존재하지 않는 팀원 프로필 이미지 업로드 시 예외 발생")
    void uploadProfileImage_memberNotFound() {
        // given
        Long memberId = 999L;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "profile.png",
                "image/png",
                "image-content".getBytes()
        );

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberProfileImageService.uploadProfileImage(memberId, file))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("ID 999 는 존재하지 않는 항목입니다.");

        verify(memberRepository, times(1)).findById(memberId);
        verify(s3Service, never()).uploadProfileImage(anyLong(), any());
        verify(s3Service, never()).deleteFile(anyString());
    }

    @Test
    @DisplayName("프로필 이미지 URL 조회 성공")
    void getProfileImageUrl_success() {
        // given
        Long memberId = 1L;
        Member member = new Member("홍길동", 20, "INTJ");
        ReflectionTestUtils.setField(member, "id", memberId);
        member.updateProfileImageKey("uploads/profiles/1/profile.png");

        String imageUrl = "https://test.cloudfront.net/uploads/profiles/1/profile.png";

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(s3Service.createDownloadUrl("uploads/profiles/1/profile.png")).thenReturn(imageUrl);

        // when
        GetProfileImageUrlResponseDto response = memberProfileImageService.getProfileImageUrl(memberId);

        // then
        assertThat(response.getProfileImageUrl()).isEqualTo(imageUrl);

        verify(memberRepository, times(1)).findById(memberId);
        verify(s3Service, times(1)).createDownloadUrl("uploads/profiles/1/profile.png");
    }

    @Test
    @DisplayName("등록된 프로필 이미지가 없으면 예외 발생")
    void getProfileImageUrl_imageNotFound() {
        // given
        Long memberId = 1L;
        Member member = new Member("홍길동", 20, "INTJ");
        ReflectionTestUtils.setField(member, "id", memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when & then
        assertThatThrownBy(() -> memberProfileImageService.getProfileImageUrl(memberId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("등록된 프로필 이미지가 없습니다.");

        verify(memberRepository, times(1)).findById(memberId);
        verify(s3Service, never()).createDownloadUrl(anyString());
    }
}