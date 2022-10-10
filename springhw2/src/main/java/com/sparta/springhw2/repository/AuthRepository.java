package com.sparta.springhw2.repository;


import com.sparta.springhw2.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByUserId(Long userId);
}
