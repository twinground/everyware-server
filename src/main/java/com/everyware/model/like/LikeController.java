package com.everyware.model.like;

import com.everyware.model.expo.BoothService;
import com.everyware.model.member.Member;
import com.everyware.model.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heart")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final BoothService boothService;

    @PostMapping("{boothId}")
    public ResponseEntity addLike(Authentication authentication, @PathVariable("boothId")Long boothId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = userService.findByEmail(userDetails.getUsername());
        likeService.addLike(boothId, member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("{boothId}")
    public ResponseEntity getLikeCount(@PathVariable("boothId")Long boothId) {
        return ResponseEntity.ok(boothService.getLikeCountById(boothId));
    }
}
