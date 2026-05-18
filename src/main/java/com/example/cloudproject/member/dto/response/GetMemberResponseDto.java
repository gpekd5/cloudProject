package com.example.cloudproject.member.dto.response;

import com.example.cloudproject.member.entity.Member;
import lombok.Getter;

@Getter
public class GetMemberResponseDto {

    private final Long id;
    private final String name;
    private final Integer age;
    private final String mbti;

    public GetMemberResponseDto(Long id, String name, Integer age, String mbti) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }

    public static GetMemberResponseDto from(Member member) {
        return new GetMemberResponseDto(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getMbti()
        );
    }
}
