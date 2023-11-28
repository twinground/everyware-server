package com.everyware.model.expo.booth.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class CommentCountResponseDTO {
    Long count;
}
