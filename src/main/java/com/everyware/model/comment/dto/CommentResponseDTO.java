package com.everyware.model.comment.dto;

import com.everyware.model.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDTO {
    String nickname;
    String comment;
    Long rate;

    public static CommentResponseDTO from(Comment comment) {
        return CommentResponseDTO.builder()
                .nickname(comment.getMember().getNickname())
                .comment(comment.getContent())
                .rate(comment.getRate())
                .build();
    }
}
