package com.everyware.model.expo.dto;

import com.everyware.model.expo.entity.Booth;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoothMaterialsResponseDTO {
    String top_logos;
    List<String> images;
    String bottom_logos;

    public static BoothMaterialsResponseDTO from(Booth booth) {
        return BoothMaterialsResponseDTO.builder()
                .top_logos(booth.getTopLogo())
                .images(booth.getImages())
                .bottom_logos(booth.getBottomLogo())
                .build();
    }

}
