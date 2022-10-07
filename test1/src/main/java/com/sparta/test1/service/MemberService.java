package com.sparta.test1.service;

//package com.example.sparta.service;

import com.sparta.test1.dto.MemberInfoResponseDto;
import com.sparta.test1.entity.Member;
import com.sparta.test1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberInfoResponseDto findMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        // Optional<Member> optionalMember = memberRepository.findById(memberId);
        // if(optionalMember.isEmpty()){ throw new DBEmptyDataException(); }
        return new MemberInfoResponseDto(member);
    }

    public List<MemberInfoResponseDto> findAllMember() {
        List<Member> memberList = memberRepository.findAll();
        //List<MemberInfoResponseDto> memberInfoResponseDtoList  = memberList.stream().map(MemberInfoResponseDto::new).collect(Collectors.toList());
        // ArrayList 사용
        List<MemberInfoResponseDto> memberInfoResponseDtoList = new ArrayList<>();
        for (Member member : memberList) {
            memberInfoResponseDtoList.add(new MemberInfoResponseDto(member));
        }

        return memberInfoResponseDtoList;

    }
}
