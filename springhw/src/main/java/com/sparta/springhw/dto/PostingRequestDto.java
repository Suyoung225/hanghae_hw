package com.sparta.springhw.dto;

import com.sparta.springhw.entity.Timestamped;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass //Timestamped
@EntityListeners(AuditingEntityListener.class) //Timestamped
public class PostingRequestDto extends Timestamped {
    private String title;
    private String author;
    private Long password;
    private String content;

    @Getter //Service에서 equals로 비교할 때 필요
    public static class PostingPasswordDto{
        private Long password;
    }
}
