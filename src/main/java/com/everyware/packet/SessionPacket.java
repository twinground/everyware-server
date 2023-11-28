package com.everyware.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Getter
@Setter
public class SessionPacket {
    private WebSocketSession webSocketSession;
    private String user_name;
    private Packet packet;
}
