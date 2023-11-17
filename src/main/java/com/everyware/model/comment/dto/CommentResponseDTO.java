package com.everyware.model.comment.dto;

import com.everyware.model.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDTO {
    String nickname;
    String content;

    public static CommentResponseDTO from(Comment comment) {
        return CommentResponseDTO.builder()
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .build();
    }
}
