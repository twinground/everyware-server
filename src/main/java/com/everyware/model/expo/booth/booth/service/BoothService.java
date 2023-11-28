package com.everyware.model.expo.booth.booth.service;

import com.everyware.common.exception.BoothHandler;
import com.everyware.common.response.status.ErrorStatus;
import com.everyware.model.expo.booth.booth.dto.BoothMeetResponseDTO;
import com.everyware.model.expo.booth.booth.repository.BoothRepository;
import com.everyware.model.expo.booth.booth.entity.Booth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoothService {

    private final BoothRepository boothRepository;

    public Booth findById(Long boothId) {
        return boothRepository.findById(boothId).orElseThrow(() -> new BoothHandler(ErrorStatus.BOOTH_NOT_EXIST));

    }

    public Integer getLikeCountById(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BoothHandler(ErrorStatus.BOOTH_NOT_EXIST));
        return booth.getLikeCount();
    }

    public BoothMeetResponseDTO makeBoothMeet(Long boothId){
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BoothHandler(ErrorStatus.BOOTH_NOT_EXIST));
        return null;
    }

}
