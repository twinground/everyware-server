package com.everyware.model.comment;

import com.everyware.model.comment.dto.CommentResponseDTO;
import com.everyware.model.comment.dto.CreateCommentRequestDTO;
import com.everyware.model.comment.entity.Comment;
import com.everyware.model.expo.BoothService;
import com.everyware.model.expo.entity.Booth;
import com.everyware.model.member.Member;
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

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getAllCommentsByBoothId(Long boothId) {
        List<CommentResponseDTO> commentResponseDTOS = commentRepository.findAllByBoothId(boothId)
                .stream().map(CommentResponseDTO::from).collect(
                        Collectors.toList());
        System.out.println(commentResponseDTOS.toString());
        return commentResponseDTOS;
    }
    public CommentResponseDTO createComment(CreateCommentRequestDTO createClubRequestDTO, Long boothId, Member member) {

        Comment comment = Comment.builder().content(createClubRequestDTO.getContent()).build();
        System.out.println(createClubRequestDTO.getContent());
        Booth booth = boothService.findById(boothId);
        comment.setBooth(booth);
        comment.setMember(member);
        commentRepository.save(comment);
        CommentResponseDTO commentResponseDTO = CommentResponseDTO.from(comment);
        return commentResponseDTO;
    }
}
