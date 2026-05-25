package com.example.cloudproject.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 팀원 정보 엔티티
 */
@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    //이름, 나이 ,MBTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 10, nullable = false)
    private String mbti;

    @Column(name = "profile_image_key")
    private String profileImageKey;

    /**
     * 팀원 기본 정보 생성자
     *
     * @param name 팀원 이름
     * @param age 팀원 나이
     * @param mbti 팀원 MBTI
     */
    public Member(String name, Integer age, String mbti) {
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }

    /**
     * 프로필 이미지 키 변경
     *
     * @param profileImageKey 프로필 이미지 S3 객체 키
     */
    public void updateProfileImageKey(String profileImageKey) {
        this.profileImageKey = profileImageKey;
    }
}
