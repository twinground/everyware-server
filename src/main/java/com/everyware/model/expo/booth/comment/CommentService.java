package com.everyware.model.expo.booth.comment;

import com.everyware.common.exception.BoothHandler;
import com.everyware.common.response.status.ErrorStatus;
import com.everyware.model.expo.booth.booth.repository.BoothRepository;
import com.everyware.model.expo.booth.comment.entity.Comment;
import com.everyware.model.expo.booth.comment.dto.CommentCountResponseDTO;
import com.everyware.model.expo.booth.comment.dto.CommentResponseDTO;
import com.everyware.model.expo.booth.comment.dto.CreateCommentRequestDTO;
import com.everyware.model.expo.booth.booth.service.BoothService;
import com.everyware.model.expo.booth.booth.entity.Booth;
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
    private final BoothRepository boothRepository;
    private final BoothService boothService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getAllCommentsByBoothId(Long boothId) {
        Booth booth = boothRepository.findById(boothId).orElseThrow(() -> new BoothHandler(ErrorStatus.BOOTH_NOT_EXIST));
        List<CommentResponseDTO> commentResponseDTOS = commentRepository.findAllByBooth(booth)
                .stream().map(CommentResponseDTO::from).collect(
                        Collectors.toList());
        return commentResponseDTOS;
    }

    @Transactional(readOnly = true)
    public CommentCountResponseDTO getCommentsCountByBoothId(Long boothId) {
        Booth booth = boothRepository.findById(boothId).orElseThrow(() -> new BoothHandler(ErrorStatus.BOOTH_NOT_EXIST));
        return CommentCountResponseDTO
                .builder().count(commentRepository.countByBooth(booth)).build();
    }

    public CommentResponseDTO createComment(CreateCommentRequestDTO createClubRequestDTO,
            Long boothId, String email) {

        Booth booth = boothService.findById(boothId);
        Member member = userService.findByEmail(email);
        System.out.println(createClubRequestDTO.getRate());
        Comment comment = Comment.builder()
                .content(createClubRequestDTO.getComment())
                .rate(Long.parseLong(createClubRequestDTO.getRate()))
                .member(member)
                .booth(booth)
                .build();
        commentRepository.save(comment);
        CommentResponseDTO commentResponseDTO = CommentResponseDTO.from(comment);
        return commentResponseDTO;
    }
}
