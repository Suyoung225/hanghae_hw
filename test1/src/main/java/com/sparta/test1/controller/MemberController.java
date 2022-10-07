package com.sparta.test1.controller;

//package com.example.sparta.controller;

import com.sparta.test1.dto.CommonResponse;
import com.sparta.test1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/{id}")
    public CommonResponse getMemberInfo(@PathVariable Long id) {
        return new CommonResponse(memberService.findMember(id));
    }

    @GetMapping("/member")
    public CommonResponse.ofList getMemberList() {
        return new CommonResponse.ofList(memberService.findAllMember());
    }



}