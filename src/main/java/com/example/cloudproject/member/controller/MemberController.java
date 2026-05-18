package com.example.cloudproject.member.controller;

import com.example.cloudproject.member.dto.request.CreateMemberRequestDto;
import com.example.cloudproject.member.dto.response.CreateMemberResponseDto;
import com.example.cloudproject.member.dto.response.GetMemberResponseDto;
import com.example.cloudproject.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    //post
    @PostMapping
    public ResponseEntity<CreateMemberResponseDto> createMember(@Valid @RequestBody CreateMemberRequestDto request) {
        log.info("[API - LOG] 팀원 등록 요청");
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(request));
    }

    //get
    @GetMapping("/{memberId}")
    public ResponseEntity<GetMemberResponseDto> findById(@PathVariable Long memberId) {
        log.info("[API - LOG] 팀원 단건 조회 요청");
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findById(memberId));
    }
}
