package com.example.cloudproject.member.service;

import com.example.cloudproject.global.exception.NotFoundException;
import com.example.cloudproject.member.dto.request.CreateMemberRequestDto;
import com.example.cloudproject.member.dto.response.CreateMemberResponseDto;
import com.example.cloudproject.member.dto.response.GetMemberResponseDto;
import com.example.cloudproject.member.entity.Member;
import com.example.cloudproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 팀원 정보 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 팀원 등록
     *
     * @param request 팀원 등록 요청
     * @return 팀원 등록 응답
     */
    @Transactional
    public CreateMemberResponseDto createMember(CreateMemberRequestDto request) {

        Member member = new Member(
                request.getName(),
                request.getAge(),
                request.getMbti()
        );

        Member saveMember = memberRepository.save(member);

        log.info("[BUSINESS EVENT] 팀원 저장 memberId={}", saveMember.getId());

        return CreateMemberResponseDto.from(saveMember);
    }

    /**
     * 팀원 단건 조회
     *
     * @param memberId 팀원 ID
     * @return 팀원 조회 응답
     */
    @Transactional(readOnly = true)
    public GetMemberResponseDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("ID " + memberId + " 는 존재하지 않는 항목입니다.")
        );

        return GetMemberResponseDto.from(member);
    }
}