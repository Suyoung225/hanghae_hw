package com.sparta.springhw2.service;

import com.sparta.springhw2.dto.CommentRequestDto;
import com.sparta.springhw2.dto.CommentResponseDto;
import com.sparta.springhw2.entity.Comment;
import com.sparta.springhw2.entity.Posting;
import com.sparta.springhw2.entity.User;
import com.sparta.springhw2.exception.ErrorCode;
import com.sparta.springhw2.exception.RequestException;
import com.sparta.springhw2.jwt.JwtTokenProvider;
import com.sparta.springhw2.repository.CommentRepository;
import com.sparta.springhw2.repository.PostingRepository;
import com.sparta.springhw2.repository.UserRepository;
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
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, HttpServletRequest request){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.POSTING_ID_NOT_FOUND_404)
        );
        String accessToken = request.getHeader("ACCESS_TOKEN");
        Claims accessClaims = jwtTokenProvider.getClaimsFormToken(accessToken);
        String username = (String)accessClaims.get("username");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));
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
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long id, HttpServletRequest request){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.COMMENT_ID_NOT_FOUND_404)
        );
        String accessToken = request.getHeader("ACCESS_TOKEN");
        Claims accessClaims = jwtTokenProvider.getClaimsFormToken(accessToken);
        String username = (String)accessClaims.get("username");
        if(!username.equals(comment.getUser().getUsername())){
            throw new RequestException(ErrorCode.EDIT_UNAUTHORIZED_403);
        }
        comment.updateComment(requestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public String deleteComment(Long id, HttpServletRequest request){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.COMMENT_ID_NOT_FOUND_404)
        );
        String accessToken = request.getHeader("ACCESS_TOKEN");
        Claims accessClaims = jwtTokenProvider.getClaimsFormToken(accessToken);
        String username = (String)accessClaims.get("username");
        if(!username.equals(comment.getUser().getUsername())){
            throw new RequestException(ErrorCode.DELETE_UNAUTHORIZED_403);
        }
        commentRepository.deleteById(id);
        return "댓글 삭제 완료";
    }

}
