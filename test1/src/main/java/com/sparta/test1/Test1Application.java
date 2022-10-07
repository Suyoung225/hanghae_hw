package com.sparta.test1;

import com.sparta.test1.dto.MemberInfoResponseDto;
import com.sparta.test1.entity.Member;
import com.sparta.test1.repository.MemberRepository;
import com.sparta.test1.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Test1Application {

    public static void main(String[] args) {
        SpringApplication.run(Test1Application.class, args);
    }
    @Bean
    public CommandLineRunner demo(MemberRepository memberRepository, MemberService memberService) {
        return (args) -> {
            memberRepository.save(new Member("윤","asdf@naver.com","pwpwpw"));
            memberRepository.save(new Member("김","1234@naver.com","pw2"));
        };
    }
}
