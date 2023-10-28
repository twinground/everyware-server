package com.twinground.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twinground.model.packet.InitBody;
import com.twinground.model.packet.Packet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class World {
    private String name;
    //private final HashSet<SessionPacket> sessions = new HashSet<>();
    private final Set<SessionPacket> sessions = ConcurrentHashMap.newKeySet();

    public static World create(@NonNull String name) {
        World created = new World();
        created.name = name;
        return created;
    }
    public void remove(WebSocketSession target) {
        String targetId = target.getId();
        boolean flag = sessions.removeIf(sessionPacket -> sessionPacket.getWebSocketSession().getId().equals(targetId));
        if (flag == true){
            String sessionId = target.getId();
            InitBody initBody = new InitBody(sessionId);
            Packet packet = new Packet(3, initBody);
            send(packet, new ObjectMapper(), sessionId);
        }
    }
    public <T> void send(T messageObject, ObjectMapper objectMapper, String excludeUserId) {
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
            sessions.parallelStream()
                    .filter(session -> !session.getWebSocketSession().getId().equals(excludeUserId))
                    .forEach(session -> WebSocketUtils.sendMessage(session.getWebSocketSession(), message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> void sendAll(T messageObject, ObjectMapper objectMapper, String excludeUserId) {
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
            sessions.parallelStream()
                    .forEach(session -> WebSocketUtils.sendMessage(session.getWebSocketSession(), message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
