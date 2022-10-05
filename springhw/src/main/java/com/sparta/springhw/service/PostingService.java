package com.sparta.springhw.service;

import com.sparta.springhw.dto.PostingRequestDto;
import com.sparta.springhw.dto.PostingResponseDto;
import com.sparta.springhw.entity.Posting;
import com.sparta.springhw.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostingService {
    private final PostingRepository postingRepository;

    // 게시글 작성
    @Transactional
    public Posting createPost(PostingRequestDto requestDto){
        Posting posting = new Posting(requestDto);
        return postingRepository.save(posting);
    }

    // 게시글 목록 조회
    @Transactional(readOnly=true)
    public List<Posting> getPostList(){
        return postingRepository.findAllByOrderByCreatedAtDesc();
    }

    // 특정 게시글 조회
    @Transactional(readOnly=true)
    public PostingResponseDto getPost(Long id){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostingResponseDto(posting);
    }

    // 비밀번호 조회
    @Transactional(readOnly=true)
    public String checkPassword(Long id, PostingRequestDto requestDto){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        return (posting.getPassword().equals(requestDto.getPassword())) ? "비밀번호가 맞습니다":"비밀번호가 아닙니다";
    }

    // 게시글 수정
    @Transactional
    public PostingResponseDto updatePost(Long id, PostingRequestDto requestDto){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        posting.updatePost(requestDto);
        return new PostingResponseDto(posting);
    }

    // 게시글 삭제
    @Transactional
    public List<Posting> deletePost(Long id){
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        postingRepository.deleteById(id);
        return postingRepository.findAllByOrderByCreatedAtDesc();
    }
}

