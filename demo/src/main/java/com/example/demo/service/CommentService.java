package com.example.demo.service;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Posting;
import com.example.demo.entity.User;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.RequestException;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostingRepository;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, User user){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.POSTING_ID_NOT_FOUND_404)
        );
        Comment comment = new Comment(requestDto,posting,user);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 댓글 목록 조회
    @Transactional(readOnly=true)
    public List<CommentResponseDto> getCommentList(Long id){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.POSTING_ID_NOT_FOUND_404)
        );
        List<Comment> comment = commentRepository.findByPostingId(id);
        return comment.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }


    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long id, User user){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.COMMENT_ID_NOT_FOUND_404)
        );
        if(!user.getUsername().equals(comment.getUser().getUsername())){
            throw new RequestException(ErrorCode.EDIT_UNAUTHORIZED_403);
        }
        comment.updateComment(requestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public String deleteComment(Long id, User user){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.COMMENT_ID_NOT_FOUND_404)
        );
        if(!user.getUsername().equals(comment.getUser().getUsername())){
            throw new RequestException(ErrorCode.DELETE_UNAUTHORIZED_403);
        }
        commentRepository.deleteById(id);
        return "댓글 삭제 완료";
    }

}
