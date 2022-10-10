package com.sparta.springhw2.dto;

import com.sparta.springhw2.entity.Posting;
import com.sparta.springhw2.entity.Timestamped;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@MappedSuperclass //Timestamped
@EntityListeners(AuditingEntityListener.class) //Timestamped
public class PostingResponseDto extends Timestamped {
    private Long id;
    private String title;
    private  String content;
    private String username;
    private Long userId;

    private List<CommentResponseDto> comments;
    public PostingResponseDto(Posting posting) {
        this.id = posting.getId();
        this.title = posting.getTitle();
        this.username = posting.getUser().getUsername();
        this.userId = posting.getUser().getId();
        this.content = posting.getContent();
        this.createdAt = posting.getCreatedAt();
        this.modifiedAt = posting.getModifiedAt();
        this.comments = posting.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
