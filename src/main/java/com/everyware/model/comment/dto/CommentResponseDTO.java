package com.everyware.model.comment.dto;

import com.everyware.model.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDTO {
    String userNickname;
    String content;

    public static CommentResponseDTO from(Comment comment) {
        return CommentResponseDTO.builder()
                .userNickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .build();
    }
}
