package com.everyware.model.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CommentCountResponseDTO {
    Long count;
}
