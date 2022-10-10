package com.sparta.springhw2.repository;


import com.sparta.springhw2.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUserId(Long userId);
}
