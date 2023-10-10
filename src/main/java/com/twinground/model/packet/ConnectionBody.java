package com.twinground.model.packet;
import com.twinground.model.packet.transfrom.TransformData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ConnectionBody extends body {
    private String session_id;
    private String expo_name;
    private List<TransformData> transforms;
}