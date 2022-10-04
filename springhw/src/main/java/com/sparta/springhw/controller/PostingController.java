package com.sparta.springhw.controller;

import com.sparta.springhw.dto.PostingRequestDto;
import com.sparta.springhw.dto.PostingResponseDto;
import com.sparta.springhw.entity.Posting;
import com.sparta.springhw.repository.PostingRepository;
import com.sparta.springhw.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostingController {
    private final PostingRepository postingRepository;
    private final PostingService postingService;


    // 게시글 작성
    @PostMapping("/api/postings")
    public Posting createPosing(@RequestBody PostingRequestDto requestDto){
        return postingService.createPost(requestDto);
    }

    // 게시글 목록 조회
    @GetMapping("/api/postings")
    public List<Posting> getPostingList(){
        return postingService.getPostList();
    }

    // 특정 게시글 조회
    @GetMapping("/api/postings/{id}")
    public PostingResponseDto getPosting(@PathVariable Long id){
        return postingService.getPost(id);
    }

    // 비밀번호 일치 여부 확인
    @PostMapping("/api/postings/{id}")
    public String checkPostingPassword(@PathVariable Long id, @RequestBody PostingRequestDto.PostingPasswordDto postingPasswordDto){
        return postingService.checkPassword(id,postingPasswordDto);
    }

    // 게시글 수정
    @PutMapping("/api/postings/{id}")
    public PostingResponseDto updatePosting(@PathVariable Long id, @RequestBody PostingRequestDto requestDto){
        postingService.updatePost(id,requestDto);
        return postingService.updatePost(id,requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/api/postings/{id}")
    public List<Posting> deletePosting(@PathVariable Long id){
        return postingService.DeletePost(id);
    }

}
