package com.everyware.model.expo.booth.booth.controller;

import com.everyware.common.response.ApiResponse;
import com.everyware.common.response.status.SuccessStatus;
import com.everyware.model.expo.booth.boothmeet.BoothMeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/booths")
@RequiredArgsConstructor
public class BoothController {
    private final BoothMeetService boothMeetService;
    @PostMapping("/{boothId}")
    public ApiResponse makeBoothMeet(@PathVariable Long boothId){
        return ApiResponse.of(SuccessStatus.CREAT_BOOTH_MEET_SUCCESS,boothMeetService.makeBoothReservation(boothId));
    }}
