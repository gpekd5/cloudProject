package com.example.cloudproject.member.repository;

import com.example.cloudproject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 팀원 JPA Repository
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
