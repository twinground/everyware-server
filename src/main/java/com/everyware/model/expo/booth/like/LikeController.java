package com.everyware.model.expo.booth.like;

import static com.everyware.common.jwt.SecurityUtil.getCurrentUserEmail;

import com.everyware.model.expo.booth.booth.service.BoothService;
import com.everyware.model.expo.booth.like.dto.LikeCountDTO;
import com.everyware.model.member.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@CrossOrigin
public class LikeController {

    private final LikeService likeService;
    private final BoothService boothService;
    private final Response response;

    @CrossOrigin(origins = "*")
    @PostMapping("{boothId}")
    public ResponseEntity addLike(@PathVariable("boothId") Long boothId) {
        if (likeService.addLike(boothId, getCurrentUserEmail())) {
            return response.success(LikeCountDTO.builder().count(boothService.getLikeCountById(boothId)).build(), "좋아요 생성 성공", HttpStatus.OK);
        } else {
            return response.success(LikeCountDTO.builder().count(boothService.getLikeCountById(boothId)).build(), "좋아요 취소 성공", HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("{boothId}")
    public ResponseEntity<?> getLikeCount(@PathVariable("boothId") Long boothId) {
        return response.success(boothService.getLikeCountById(boothId)
                , "좋아요 개수 가져오기 성공", HttpStatus.OK);
    }
}
