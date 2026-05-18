package com.example.cloudproject.member.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateMemberRequestDto {

    @NotBlank(message = "이름은 필수입니다.")
    private final String name;

    @NotNull(message = "나이는 필수입니다.")
    @Min(value = 1,  message = "나이는 1이상어야 합니다.")
    private final Integer age;

    @NotBlank(message = "MBTI는 필수입니다.")
    @Pattern(
            regexp = "^(INTJ|INTP|ENTJ|ENTP|INFJ|INFP|ENFJ|ENFP|ISTJ|ISFJ|ESTJ|ESFJ|ISTP|ISFP|ESTP|ESFP)$",
            message = "올바른 MBTI 형식이 아닙니다.")
    private final String mbti;

    public CreateMemberRequestDto(String name, Integer age, String mbti) {
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }
}
