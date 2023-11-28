package com.everyware.model.expo.booth.boothmeet;

import com.everyware.common.exception.BoothHandler;
import com.everyware.common.response.status.ErrorStatus;
import com.everyware.model.expo.booth.booth.dto.BoothMeetResponseDTO;
import com.everyware.model.expo.booth.booth.repository.BoothRepository;
import com.everyware.model.expo.booth.booth.service.BoothService;
import com.everyware.model.expo.booth.booth.entity.Booth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoothMeetService {
    private final BoothService boothService;
    private final BoothMeetRepository boothMeetRepository;

    public BoothMeetResponseDTO makeBoothReservation(Long boothId) {
        Booth booth = boothService.findById(boothId);
        if(!boothMeetRepository.existsByBoothAndMeetReserveTimeAfter(booth, LocalDateTime.now())){
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime nextReservationTime = currentTime.plusMinutes(10 - (currentTime.getMinute() % 10));
            BoothMeet boothMeet = new BoothMeet(booth,nextReservationTime);
            boothMeetRepository.save(boothMeet);
            return BoothMeetResponseDTO.from(boothMeet);
        }
        else {
            // 가장 최근에 예약된 BoothMeet 가져오기
            BoothMeet latestReservation = boothMeetRepository.findTopByBoothOrderByMeetReserveTimeDesc(booth);

            // 예약된 시간이 없거나, 현재 시간보다 이후일 경우에만 예약 생성
            if (latestReservation != null && latestReservation.getMeetReserveTime().isAfter(LocalDateTime.now())) {
                // 가장 최근에 예약된 시간을 기준으로 10분 단위로 다음 예약 시간 계산
                LocalDateTime nextReservationTime = latestReservation.getMeetReserveTime().plusMinutes(10 - (latestReservation.getMeetReserveTime().getMinute() % 10));

                BoothMeet boothMeet = new BoothMeet(booth, nextReservationTime);
                boothMeetRepository.save(boothMeet);
                return BoothMeetResponseDTO.from(boothMeet);
            }
        }
        throw new BoothHandler(ErrorStatus.BOOTH_MEETING_ERROR);
    }
}
