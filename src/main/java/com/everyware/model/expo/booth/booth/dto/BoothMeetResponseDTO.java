package com.everyware.model.expo.booth.booth.dto;

import com.everyware.model.expo.booth.boothmeet.BoothMeet;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Builder
@Getter
public class BoothMeetResponseDTO {
    private LocalDateTime reservation_time;
    private Long timestamp;
    private String booth_name;

    public static BoothMeetResponseDTO from(BoothMeet boothMeet) {
        return BoothMeetResponseDTO.builder()
                .reservation_time(boothMeet.getMeetReserveTime())
                .timestamp(calculateEpochTimestamp(boothMeet.getMeetReserveTime()))
                .booth_name(boothMeet.getBooth().getTitle())
                .build();
    }

    private static Long calculateEpochTimestamp(LocalDateTime reservationTime) {
        return reservationTime.toEpochSecond(ZoneOffset.UTC);
    }
}
