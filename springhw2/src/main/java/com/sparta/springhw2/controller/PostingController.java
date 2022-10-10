package com.sparta.springhw2.controller;

import com.sparta.springhw2.dto.PostingListResponseDto;
import com.sparta.springhw2.dto.PostingRequestDto;
import com.sparta.springhw2.dto.PostingResponseDto;
import com.sparta.springhw2.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostingController {

    private final PostingService postingService;

    // 게시글 작성
    @PostMapping("/auth/postings")
    public PostingResponseDto createPosing(@RequestBody PostingRequestDto requestDto, HttpServletRequest request){
        return postingService.createPost(requestDto,request);
    }

    // 게시글 목록 조회
    @GetMapping("/postings")
    public List<PostingListResponseDto> getPostingList(){
        return postingService.getPostList();
    }

    // 특정 게시글 조회
    @GetMapping("/postings/{id}")
    public PostingResponseDto getPosting(@PathVariable Long id){
        return postingService.getPost(id);
    }

    // 게시글 수정
    @PutMapping("/auth/postings/{id}")
    public PostingResponseDto updatePosing(@RequestBody PostingRequestDto requestDto,@PathVariable Long id, HttpServletRequest request){
        return postingService.updatePost(requestDto,id,request);
    }

    // 게시글 삭제
    @DeleteMapping ("/auth/postings/{id}")
    public String deletePosting(@PathVariable Long id, HttpServletRequest request){
        return postingService.deletePost(id, request);
    }


}
