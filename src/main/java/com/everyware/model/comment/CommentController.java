package com.everyware.model.comment;

import static com.everyware.model.jwt.SecurityUtil.getCurrentUserEmail;

import com.everyware.model.comment.dto.CommentCountResponseDTO;
import com.everyware.model.comment.dto.CommentResponseDTO;
import com.everyware.model.comment.dto.CreateCommentRequestDTO;
import com.everyware.model.jwt.Helper;
import com.everyware.model.member.Member;
import com.everyware.model.member.dto.Response;
import com.everyware.model.member.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
        return ResponseEntity.ok(commentResponseDTOS);
    }
    @GetMapping("/{boothId}/count")
    public ResponseEntity getBoothCommentsCount(@PathVariable Long boothId) {
        CommentCountResponseDTO commentResponseDTOS = commentService.getCommentsCountByBoothId(boothId);
        return ResponseEntity.ok(commentResponseDTOS);
    }

    @CrossOrigin
    @PostMapping("/{boothId}")
    public ResponseEntity createBoothComments(@PathVariable Long boothId,
            @RequestBody CreateCommentRequestDTO createCommentRequestDto
           ) {
        CommentResponseDTO commentResponseDTO = commentService.createComment(createCommentRequestDto, boothId,
                getCurrentUserEmail());
        return ResponseEntity.ok(commentResponseDTO);
    }
}
