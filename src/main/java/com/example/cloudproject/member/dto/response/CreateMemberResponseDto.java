package com.example.cloudproject.member.dto.response;

import com.example.cloudproject.member.entity.Member;
import lombok.Getter;

/**
 * 팀원 등록 응답 DTO
 */
@Getter
public class CreateMemberResponseDto {

    private final Long id;
    private final String name;
    private final Integer age;
    private final String mbti;

    /**
     * 팀원 등록 응답 생성자
     *
     * @param id 팀원 ID
     * @param name 팀원 이름
     * @param age 팀원 나이
     * @param mbti 팀원 MBTI
     */
    public CreateMemberResponseDto(Long id, String name, Integer age, String mbti) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }

    /**
     * 팀원 엔티티 기반 등록 응답 변환
     *
     * @param member 팀원 엔티티
     * @return 팀원 등록 응답
     */
    public static CreateMemberResponseDto from(Member member) {
        return new CreateMemberResponseDto(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getMbti()
        );
    }
}
