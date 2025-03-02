package com.kangwon.webflux1.dto;

import com.kangwon.webflux1.repository.Post;
import com.kangwon.webflux1.service.PostService;
import com.kangwon.webflux1.service.PostServiceV2;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostResponseV2 {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static PostResponseV2 of(Post post) {
        return PostResponseV2.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdDate(post.getCreatedAt())
                .modifiedDate(post.getUpdatedAt())
                .build();
    }
}