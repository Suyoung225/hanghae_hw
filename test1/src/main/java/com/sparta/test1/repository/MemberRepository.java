package com.sparta.test1.repository;

import com.sparta.test1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member,Long> {
}
