package com.twinground.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.HashSet;
import java.util.UUID;

@Getter
public class World {
    private String name;
    private final HashSet<SessionPacket> sessions = new HashSet<>();

    public static World create(@NonNull String name) {
        World created = new World();
        created.name = name;
        return created;
    }
    public void remove(WebSocketSession target) {
        String targetId = target.getId();
        sessions.removeIf(sessionPacket -> sessionPacket.getWebSocketSession().getId().equals(targetId));
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

}
