package com.example.demo.repository;

import com.example.demo.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting,Long> {
    List<Posting> findAllByOrderByCreatedAtDesc();
}
