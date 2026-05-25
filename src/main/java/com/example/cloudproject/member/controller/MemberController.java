package com.example.cloudproject.member.controller;

import com.example.cloudproject.member.dto.request.CreateMemberRequestDto;
import com.example.cloudproject.member.dto.response.CreateMemberResponseDto;
import com.example.cloudproject.member.dto.response.GetMemberResponseDto;
import com.example.cloudproject.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 팀원 정보 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 팀원 등록 API
     *
     * @param request 팀원 등록 요청
     * @return 팀원 등록 응답
     */
    @PostMapping
    public ResponseEntity<CreateMemberResponseDto> createMember(
            @Valid @RequestBody CreateMemberRequestDto request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.createMember(request));
    }

    /**
     * 팀원 단건 조회 API
     *
     * @param memberId 팀원 ID
     * @return 팀원 조회 응답
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<GetMemberResponseDto> findById(
            @PathVariable Long memberId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findById(memberId));
    }
}