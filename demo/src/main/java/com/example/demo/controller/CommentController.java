package com.example.demo.controller;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"댓글 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성 id: post id
    @PostMapping("/auth/comments/{id}")
    public CommentResponseDto create(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(id, requestDto,userDetails.getUser());
    }

    // 댓글 목록 조회
    @GetMapping("/comments/{id}") //posting id
    public List<CommentResponseDto> getList(@PathVariable Long id){
        return commentService.getCommentList(id);
    }

    // 댓글 수정
    @PutMapping("/auth/comments/{id}") // comment id
    public CommentResponseDto update(@RequestBody CommentRequestDto requestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(requestDto,id,userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping ("/auth/comments/{id}")  // comment id
    public String delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }
}
