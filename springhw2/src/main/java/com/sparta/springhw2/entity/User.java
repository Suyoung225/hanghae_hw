package com.sparta.springhw2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, length= 12, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password",nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Posting> posting = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Comment> comments = new ArrayList<>();

}
