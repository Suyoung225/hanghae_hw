package com.sparta.springhw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 안 붙여주면 createdAt, modifiedAt null값 나옴
@SpringBootApplication
public class SpringhwApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringhwApplication.class, args);
    }

}
