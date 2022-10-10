package com.sparta.springhw2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.springhw2.dto.PostingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "posting")
public class Posting extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "posting_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "posting")
    List<Comment> comments = new ArrayList<>();

    // 게시글 생성 시
    public Posting(PostingRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
    }

    // 게시글 수정
    public void updatePost(PostingRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
