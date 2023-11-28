package com.everyware.model.expo.dto;


import com.everyware.model.expo.entity.Booth;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoothResponseDTO {

    private Long id;
    private String title;
    private String meetLink;
    private BoothMaterialsResponseDTO boothMaterials;

    public static BoothResponseDTO from(Booth booth) {
        return BoothResponseDTO.builder()
                .id(booth.getId())
                .title(booth.getTitle())
                .meetLink(booth.getMeetLink())
                .boothMaterials(BoothMaterialsResponseDTO.from(booth))
                .build();
    }
}

