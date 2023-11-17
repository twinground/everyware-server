package com.everyware.model.expo.dto;


import com.everyware.model.expo.entity.Booth;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoothResponseDTO {

    private Long id;
    private String title;
    private BoothMaterialsResponseDTO boothMaterials;

    public static BoothResponseDTO from(Booth booth) {
        return BoothResponseDTO.builder()
                .id(booth.getId())
                .title(booth.getTitle())
                .boothMaterials(BoothMaterialsResponseDTO.from(booth))
                .build();
    }
}

