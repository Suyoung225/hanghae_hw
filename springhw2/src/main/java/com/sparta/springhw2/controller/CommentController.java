package com.sparta.springhw2.controller;

import com.sparta.springhw2.dto.CommentRequestDto;
import com.sparta.springhw2.dto.CommentResponseDto;
import com.sparta.springhw2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성 id: post id
    @PostMapping("/auth/comments/{id}")
    public CommentResponseDto create(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(id, requestDto,request);
    }

    // 댓글 목록 조회
    @GetMapping("/comments/{id}") //posting id
    public List<CommentResponseDto> getList(@PathVariable Long id, HttpServletRequest request){
        return commentService.getCommentList(id, request);
    }

    // 댓글 수정
    @PutMapping("/auth/comments/{id}") // comment id
    public CommentResponseDto update(@RequestBody CommentRequestDto requestDto, @PathVariable Long id, HttpServletRequest request){
        return commentService.updateComment(requestDto,id,request);
    }

    // 댓글 삭제
    @DeleteMapping ("/auth/comments/{id}")  // comment id
    public String delete(@PathVariable Long id, HttpServletRequest request){
        return commentService.deleteComment(id, request);
    }
}
