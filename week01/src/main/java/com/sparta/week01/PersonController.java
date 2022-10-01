package com.sparta.week01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @GetMapping("/myinfo")
    public Person getPerson(){
        Person person = new Person();
        person.nickname = "별명";
        person.setName("윤모씨");
        person.setAge(100);
        return person;
    }

}
