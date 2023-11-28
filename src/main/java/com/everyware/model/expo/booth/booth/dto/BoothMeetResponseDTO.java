package com.everyware.model.expo.booth.booth.dto;

import com.everyware.model.expo.booth.boothmeet.BoothMeet;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class BoothMeetResponseDTO {
    String reservation_time;

    public static BoothMeetResponseDTO from(BoothMeet boothMeet){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분");
        String formattedDateTime = boothMeet.getMeetReserveTime().format(formatter);
        return BoothMeetResponseDTO.builder()
                .reservation_time(formattedDateTime)
                .build();
    }
}
