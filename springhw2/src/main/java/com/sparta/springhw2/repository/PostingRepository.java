package com.sparta.springhw2.repository;

import com.sparta.springhw2.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting,Long> {
    List<Posting> findAllByOrderByCreatedAtDesc();
}
