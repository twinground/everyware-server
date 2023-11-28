package com.everyware.model.expo.booth.comment;

import static com.everyware.common.jwt.SecurityUtil.getCurrentUserEmail;

import com.everyware.model.expo.booth.comment.dto.CommentCountResponseDTO;
import com.everyware.model.expo.booth.comment.dto.CommentResponseDTO;
import com.everyware.model.expo.booth.comment.dto.CreateCommentRequestDTO;
import com.everyware.model.member.dto.Response;
import com.everyware.model.member.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final Response response;

    @GetMapping("/{boothId}")
    public ResponseEntity getBoothComments(@PathVariable Long boothId) {
        List<CommentResponseDTO> commentResponseDTOS = commentService.getAllCommentsByBoothId(
                boothId);
        return response.success(commentResponseDTOS,"댓글들 조회 성공",HttpStatus.OK);
    }
    @GetMapping("/{boothId}/count")
    public ResponseEntity getBoothCommentsCount(@PathVariable Long boothId) {
        CommentCountResponseDTO commentResponseDTOS = commentService.getCommentsCountByBoothId(boothId);
        return response.success(commentResponseDTOS,"댓글 개수 조회 성공",HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{boothId}")
    public ResponseEntity<?> createBoothComments(@PathVariable Long boothId,
            @RequestBody CreateCommentRequestDTO createCommentRequestDto
           ) {
        CommentResponseDTO commentResponseDTO = commentService.createComment(createCommentRequestDto, boothId,
                getCurrentUserEmail());
        return response.success(commentService.getCommentsCountByBoothId(boothId),"댓글 생성 성공", HttpStatus.CREATED);
    }
}
