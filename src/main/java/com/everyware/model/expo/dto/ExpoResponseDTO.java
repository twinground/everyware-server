package com.everyware.model.expo.dto;


import com.everyware.model.expo.entity.Expo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExpoResponseDTO {
    private Long id;
    private String expoImageUrl;
    private String title;
    private String introduction;

    public static ExpoResponseDTO from(Expo expo) {
        return ExpoResponseDTO.builder()
                .id(expo.getId())
                .expoImageUrl(expo.getExpoImageUrl())
                .title(expo.getTitle())
                .introduction(expo.getIntroduction())
                .build();
    }

}
