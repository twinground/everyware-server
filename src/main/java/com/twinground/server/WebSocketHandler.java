package com.twinground.server;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twinground.model.packet.ConnectionBody;
import com.twinground.model.packet.InitBody;
import com.twinground.model.packet.Packet;
import com.twinground.model.packet.TransformBody;
import com.twinground.model.packet.transfrom.ITransform;
import com.twinground.model.packet.transfrom.Position;
import com.twinground.model.packet.transfrom.Quaternion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler{
    private final ObjectMapper objectMapper;
    private HashSet<SessionPacket> sessions = new HashSet<>();
    private final static Logger LOG = Logger.getGlobal();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        /*
        sessionId를 갖고 있는 Session의 Packet을 업데이트.
        그러기 위해서는 input을 Packet 객체로 역직렬화
         */
        String input = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(input);
        int id = jsonNode.get("type").asInt();
        if (id == 1) {
            ConnectionBody bodyObject = objectMapper.treeToValue(jsonNode.get("body"), ConnectionBody.class);
            bodyObject.setSession_id(session.getId());
            ArrayList<TransformBody> transforms = new ArrayList<>();
            for (SessionPacket sessionPacket : sessions) {
                if (!sessionPacket.getWebSocketSession().getId().equals(session.getId())) {
                    transforms.add((TransformBody) sessionPacket.getPacket().getBody());
                }
            }
            bodyObject.setTransforms(transforms);
            Packet updatepacket = new Packet(id, bodyObject);
            Packet defaultPacket = new Packet(2, initTransform(session.getId()));
            SessionPacket sessionPacket= new SessionPacket(session, defaultPacket);
            String connectionJson = objectMapper.writeValueAsString(updatepacket);
            TextMessage textMessage = new TextMessage(connectionJson);
            session.sendMessage(textMessage);
            sessions.add(sessionPacket);
        }
        //transform 변환하면 보내는 코드
        else if (id == 2) {
            TransformBody bodyObject = objectMapper.treeToValue(jsonNode.get("body"), TransformBody.class);
            Packet updatepacket = new Packet(id, bodyObject);
            String sessionId = session.getId();
            for (SessionPacket sessionPacket : sessions) {
                if (sessionPacket.getWebSocketSession().getId().equals(session.getId())) {
                    sessionPacket.setPacket(updatepacket);
                    break;
                }
            }
            send(updatepacket,objectMapper,sessionId);
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /*
        1. 연결 시도한 세션에게 세션 관련 정보 보내기
        */
        String sessionId = session.getId();
        InitBody initBody = new InitBody(sessionId);
        Packet packet = new Packet(0, initBody);
        String connectionJson = objectMapper.writeValueAsString(packet);
        TextMessage textMessage = new TextMessage(connectionJson);
        session.sendMessage(textMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.removeIf(sessionPacket -> sessionPacket.getWebSocketSession().equals(session));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }

    private <T> void send(T messageObject, ObjectMapper objectMapper, String excludeUserId) {
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));

            sessions.parallelStream()
                    .filter(session -> !session.getWebSocketSession().getId().equals(excludeUserId))
                    .forEach(session -> WebSocketUtils.sendMessage(session.getWebSocketSession(), message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private TransformBody initTransform(String sessionId){
        Position position = new Position(0,0);
        Quaternion quaternion = new Quaternion(0,0);
        ITransform iTransform = new ITransform(position,quaternion,"idle");
        TransformBody transformBody = new TransformBody(sessionId, iTransform);
        return transformBody;
    }
}
