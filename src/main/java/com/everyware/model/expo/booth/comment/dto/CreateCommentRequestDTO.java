package com.everyware.model.expo.booth.comment.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequestDTO {
    @NotBlank(message = "내용은 반드시 입력되어야합니다.")
    String comment;
    @NotBlank(message = "Rate는 반드시 입력되어야합니다.")
    String rate;
}
