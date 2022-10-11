package com.example.demo.entity;

import com.example.demo.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "postingId")
    private Posting posting;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // 댓글 생성 시
    public Comment(CommentRequestDto requestDto, Posting posting, User user){
        this.content = requestDto.getContent();
        this.posting = posting;
        this.user = user;
    }

    // 댓글 수정
    public void updateComment(CommentRequestDto requestDto){
        this.content = requestDto.getContent();
    }


}
