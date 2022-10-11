package com.example.demo.service;


import com.example.demo.dto.PostingListResponseDto;
import com.example.demo.dto.PostingRequestDto;
import com.example.demo.dto.PostingResponseDto;
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
public class PostingService {
    private final PostingRepository postingRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 게시글 작성
    @Transactional
    public PostingResponseDto createPost(PostingRequestDto requestDto, User user){
        Posting posting = new Posting(requestDto,user);
        postingRepository.save(posting);
        return new PostingResponseDto(posting);
    }

    // 게시글 목록 조회
    @Transactional(readOnly=true)
    public List<PostingListResponseDto> getPostList(){
        List<Posting> posting = postingRepository.findAllByOrderByCreatedAtDesc();
        List<PostingListResponseDto> postDtoList = posting.stream().map(PostingListResponseDto::new).collect(Collectors.toList());
        return postDtoList;
    }

    // 특정 게시글 조회
    @Transactional(readOnly=true)
    public PostingResponseDto getPost(Long id){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.POSTING_ID_NOT_FOUND_404)
        );
        return new PostingResponseDto(posting);
    }

    // 게시글 수정
    @Transactional
    public PostingResponseDto updatePost(PostingRequestDto requestDto, Long id, User user){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.POSTING_ID_NOT_FOUND_404)
        );
        if(!user.getUsername().equals(posting.getUser().getUsername())){
            throw new RequestException(ErrorCode.EDIT_UNAUTHORIZED_403);
        }
        posting.updatePost(requestDto);
        return new PostingResponseDto(posting);
    }

    // 게시글 삭제
    @Transactional
    public String deletePost(Long id, User user){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.POSTING_ID_NOT_FOUND_404)
        );
        if(!user.getUsername().equals(posting.getUser().getUsername())){
            throw new RequestException(ErrorCode.DELETE_UNAUTHORIZED_403);
        }
        List<Comment> commentList =  commentRepository.findByPostingId(id);
        commentRepository.deleteAll(commentList);
        postingRepository.deleteById(id);
        return "해당 게시글과 댓글 모두 삭제 완료";
    }

}
