package com.everyware.packet;
import com.everyware.packet.transform.TransformData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ConnectionBody extends body {
    private String session_id;
    private String user_name;
    private String expo_name;
    private List<TransformData> transforms;
}
