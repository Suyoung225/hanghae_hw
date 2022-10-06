package com.sparta.springhw.controller;

import com.sparta.springhw.dto.PostingRequestDto;
import com.sparta.springhw.dto.PostingResponseDto;
import com.sparta.springhw.dto.ResponseMessageDto;
import com.sparta.springhw.entity.Posting;
import com.sparta.springhw.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostingController {
    private final PostingService postingService;

    // 게시글 작성
    @PostMapping("/api/postings")
    public ResponseMessageDto<PostingResponseDto> createPosing(@RequestBody PostingRequestDto requestDto){
        try{
            return new ResponseMessageDto<>(true,postingService.createPost(requestDto), null);
        }catch (Exception e){
            return new ResponseMessageDto<>(false,null, "잘못된 데이터 형식입니다");
        }
    }

    // 게시글 목록 조회
    @GetMapping("/api/postings")
    public ResponseMessageDto<List<PostingResponseDto>> getPostingList(){
        return new ResponseMessageDto<>(true,postingService.getPostList(), null);
    }

    // 특정 게시글 조회
    @GetMapping("/api/postings/{id}")
    public ResponseMessageDto<PostingResponseDto> getPosting(@PathVariable Long id){
        try{
            return new ResponseMessageDto<>(true,postingService.getPost(id), null);
        }catch (Exception e){
            return new ResponseMessageDto<>(false,null, "해당 아이디가 존재하지 않습니다");
        }
    }

    // 비밀번호 일치 여부 확인
    @PostMapping("/api/postings/{id}")
    public ResponseMessageDto<String> checkPostingPassword(@PathVariable Long id, @RequestBody PostingRequestDto requestDto){
        try{
            return new ResponseMessageDto<>(true,postingService.checkPassword(id,requestDto), null);
        }catch (IllegalArgumentException e){
            return new ResponseMessageDto<>(false,null, "해당 아이디가 존재하지 않습니다");
        }catch(Exception e){
            return new ResponseMessageDto<>(false,null, "잘못된 데이터 형식입니다");
        }
    }

    // 게시글 수정
    @PutMapping("/api/postings/{id}")
    public ResponseMessageDto<PostingResponseDto> updatePosting(@PathVariable Long id, @RequestBody PostingRequestDto requestDto){
        try{
            return new ResponseMessageDto<>(true,postingService.updatePost(id,requestDto), null);
        }catch (IllegalArgumentException e){
            return new ResponseMessageDto<>(false,null, "해당 아이디가 존재하지 않습니다");
        }catch(Exception e){
            return new ResponseMessageDto<>(false,null, "잘못된 데이터 형식입니다");
        }
    }


    // 게시글 삭제
    @DeleteMapping("/api/postings/{id}")
    public ResponseMessageDto<List<Posting>> deletePosing(@PathVariable Long id){
        try{
            return new ResponseMessageDto<>(true,postingService.deletePost(id), null);
        }catch (Exception e){
            return new ResponseMessageDto<>(false,null, "해당 아이디가 존재하지 않습니다");
        }
    }

}
