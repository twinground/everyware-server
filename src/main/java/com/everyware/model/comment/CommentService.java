package com.everyware.model.comment;

import com.everyware.model.comment.dto.CommentCountResponseDTO;
import com.everyware.model.comment.dto.CommentResponseDTO;
import com.everyware.model.comment.dto.CreateCommentRequestDTO;
import com.everyware.model.comment.entity.Comment;
import com.everyware.model.expo.BoothService;
import com.everyware.model.expo.entity.Booth;
import com.everyware.model.member.Member;
import com.everyware.model.member.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoothService boothService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getAllCommentsByBoothId(Long boothId) {
        List<CommentResponseDTO> commentResponseDTOS = commentRepository.findAllByBoothId(boothId)
                .stream().map(CommentResponseDTO::from).collect(
                        Collectors.toList());
        return commentResponseDTOS;
    }

    @Transactional(readOnly = true)
    public CommentCountResponseDTO getCommentsCountByBoothId(Long boothId) {
        return CommentCountResponseDTO
                .builder().count(commentRepository.countByBoothId(boothId)).build();
    }

    public CommentResponseDTO createComment(CreateCommentRequestDTO createClubRequestDTO,
            Long boothId, String email) {

        Booth booth = boothService.findById(boothId);
        Member member = userService.findByEmail(email);
        Comment comment = Comment.builder()
                .content(createClubRequestDTO.getContent())
                .member(member)
                .booth(booth)
                .build();
        commentRepository.save(comment);
        System.out.println(comment.getMember().getNickname());
        CommentResponseDTO commentResponseDTO = CommentResponseDTO.from(comment);
        return commentResponseDTO;
    }
}
