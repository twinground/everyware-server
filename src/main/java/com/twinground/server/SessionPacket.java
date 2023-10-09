package com.twinground.server;

import com.twinground.model.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Getter
@Setter
public class SessionPacket {
    private WebSocketSession webSocketSession;
    private Packet packet;
}
