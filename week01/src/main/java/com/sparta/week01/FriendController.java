package com.sparta.week01;

import com.sparta.week01.domain2.Friend;
import com.sparta.week01.domain2.FriendRepository;
import com.sparta.week01.domain2.FriendRequestDto;
import com.sparta.week01.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;
    private final FriendRepository friendRepository;

    @GetMapping("/api/friends")
    public List<Friend> getFriends() {
        return friendRepository.findAll();
    }

    @PostMapping("/api/friends")
    public Friend createFriend(@RequestBody FriendRequestDto requestDto) {
        Friend friend = new Friend(requestDto);
        return friendRepository.save(friend);
    }

    @PutMapping("/api/friends/{id}")
    public Long updateFriend(@PathVariable Long id, @RequestBody FriendRequestDto requestDto) {
        return friendService.update(id, requestDto);
    }

    @DeleteMapping("/api/friends/{id}")
    public Long deletePerson(@PathVariable Long id) {
        friendRepository.deleteById(id);
        return id;
    }
}
