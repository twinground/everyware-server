package com.everyware.model.comment;

import com.everyware.model.comment.dto.CommentResponseDTO;
import com.everyware.model.comment.dto.CreateCommentRequestDTO;
import com.everyware.model.member.User;
import com.everyware.model.member.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/{boothId}")
    public ResponseEntity getBoothComments(@PathVariable Long boothId) {
        List<CommentResponseDTO> commentResponseDTOS = commentService.getAllCommentsByBoothId(
                boothId);
        return ResponseEntity.ok(commentResponseDTOS);
    }

    @PostMapping("/{boothId}")
    public ResponseEntity createBoothComments(@PathVariable Long boothId,
            @RequestBody CreateCommentRequestDTO createCommentRequestDto,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());
        CommentResponseDTO commentResponseDTO = commentService.createComment(createCommentRequestDto, boothId, user);
        return ResponseEntity.ok(commentResponseDTO);
    }
}
