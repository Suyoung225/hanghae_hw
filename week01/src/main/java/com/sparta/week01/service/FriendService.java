package com.sparta.week01.service;

import com.sparta.week01.domain2.Friend;
import com.sparta.week01.domain2.FriendRepository;
import com.sparta.week01.domain2.FriendRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional
    public Long update(Long id, FriendRequestDto requestDto){
        Friend friend = friendRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 존재하지 않습니다.")
        );
        friend.update(requestDto);
        return friend.getId();
    }


}
