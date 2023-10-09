package com.twinground.model.packet;
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
    private List<TransformBody> transforms;
}