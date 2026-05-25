package com.example.cloudproject.member.service;

import com.example.cloudproject.global.exception.NotFoundException;
import com.example.cloudproject.member.dto.request.CreateMemberRequestDto;
import com.example.cloudproject.member.dto.response.CreateMemberResponseDto;
import com.example.cloudproject.member.dto.response.GetMemberResponseDto;
import com.example.cloudproject.member.entity.Member;
import com.example.cloudproject.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 팀원 정보 서비스 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    /**
     * 팀원 등록 성공 검증
     */
    @Test
    @DisplayName("팀원 등록 성공")
    void createMember_success() {
        // given
        CreateMemberRequestDto request = new CreateMemberRequestDto(
                "홍길동",
                20,
                "INTJ"
        );

        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        });

        // when
        CreateMemberResponseDto response = memberService.createMember(request);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("홍길동");
        assertThat(response.getAge()).isEqualTo(20);
        assertThat(response.getMbti()).isEqualTo("INTJ");

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    /**
     * 팀원 단건 조회 성공 검증
     */
    @Test
    @DisplayName("팀원 단건 조회 성공")
    void findById_success() {
        // given
        Member member = new Member("홍길동", 20, "INTJ");
        ReflectionTestUtils.setField(member, "id", 1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // when
        GetMemberResponseDto response = memberService.findById(1L);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("홍길동");
        assertThat(response.getAge()).isEqualTo(20);
        assertThat(response.getMbti()).isEqualTo("INTJ");

        verify(memberRepository, times(1)).findById(1L);
    }

    /**
     * 존재하지 않는 팀원 조회 예외 검증
     */
    @Test
    @DisplayName("존재하지 않는 팀원 조회 시 예외 발생")
    void findById_notFound() {
        // given
        when(memberRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.findById(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("ID 999 는 존재하지 않는 항목입니다.");

        verify(memberRepository, times(1)).findById(999L);
    }
}