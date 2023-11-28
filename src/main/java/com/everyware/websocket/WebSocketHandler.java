package com.everyware.websocket;
import com.everyware.world.WorldRepository;
import com.everyware.packet.SessionPacket;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.everyware.packet.ChattingBody;
import com.everyware.packet.ConnectionBody;
import com.everyware.packet.InitBody;
import com.everyware.packet.Packet;
import com.everyware.packet.TransformBody;
import com.everyware.packet.transform.ITransform;
import com.everyware.packet.transform.Position;
import com.everyware.packet.transform.Quaternion;
import com.everyware.packet.transform.TransformData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler{
    private final ObjectMapper objectMapper;
    private final WorldRepository worldRepository;

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
            String nickname = bodyObject.getUser_name();
            bodyObject.setUser_name(nickname);
            ArrayList<TransformData> transforms = new ArrayList<>();
            String expo_name = bodyObject.getExpo_name();
            Set<SessionPacket> sessions = worldRepository.getWorld(expo_name).getSessions();
            Iterator<SessionPacket> sessionPacketIterator = sessions.iterator();
            while (sessionPacketIterator.hasNext()) {
                SessionPacket sessionPacket = sessionPacketIterator.next();
                if (!sessionPacket.getWebSocketSession().getId().equals(session.getId())) {
                    TransformBody tmp = (TransformBody) sessionPacket.getPacket().getBody();
                    transforms.add(tmp.toTransformDataFromSession(sessionPacket.getUser_name()));
                }
            }
            bodyObject.setTransforms(transforms);
            Packet updatepacket = new Packet(id, bodyObject);
            TransformBody initTransformBody = initTransform(session.getId(), nickname, bodyObject.getExpo_name());
            Packet defaultPacket = new Packet(2, initTransformBody);
            SessionPacket sessionPacket= new SessionPacket(session, nickname, defaultPacket);
            String connectionJson = objectMapper.writeValueAsString(updatepacket);
            TextMessage textMessage = new TextMessage(connectionJson);
            synchronized(session) {
                session.sendMessage(textMessage);
            }
            sessions.add(sessionPacket);
            ArrayList<TransformData> transforms1 = new ArrayList<>();
            transforms1.add(initTransformBody.toTransformData());
            bodyObject.setTransforms(transforms1);
            updatepacket.setBody(bodyObject);
            worldRepository.getWorld(expo_name).send(updatepacket, objectMapper, session.getId());
        }
        //transform 변환하면 보내는 코드
        else if (id == 2) {
            TransformBody bodyObject = objectMapper.treeToValue(jsonNode.get("body"), TransformBody.class);
            Packet updatepacket = new Packet(id, bodyObject);
            String sessionId = session.getId();
            String expo_name = bodyObject.getExpo_name();
            Set<SessionPacket> sessions = worldRepository.getWorld(expo_name).getSessions();
            Iterator<SessionPacket> sessionPacketIterator = sessions.iterator();
            while (sessionPacketIterator.hasNext()) {
                SessionPacket sessionPacket = sessionPacketIterator.next();
                if (sessionPacket.getWebSocketSession().getId().equals(sessionId)) {
                    sessionPacket.setPacket(updatepacket);
                    break;
                }
            }
            worldRepository.getWorld(expo_name).send(updatepacket,objectMapper,sessionId);
        } else if (id == 4) {
            ChattingBody bodyObject = objectMapper.treeToValue(jsonNode.get("body"), ChattingBody.class);
            Packet chattingPacket = new Packet(id, bodyObject);
            String expo_name = bodyObject.getExpo_name();
            worldRepository.getWorld(expo_name).sendAll(chattingPacket,objectMapper);
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
        synchronized(session) {
            session.sendMessage(textMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        InitBody initBody = new InitBody(sessionId);
        Packet packet = new Packet(3, initBody);
        String connectionJson = objectMapper.writeValueAsString(packet);
        TextMessage textMessage = new TextMessage(connectionJson);
        worldRepository.remove(session);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket transport error: " + exception.getMessage(), exception);
    }


    private TransformBody initTransform(String sessionId, String user_name,String expo_name){
        Position position = new Position(0,0);
        Quaternion quaternion = new Quaternion(0,0);
        ITransform iTransform = new ITransform(position,quaternion,"idle");
        return new TransformBody(sessionId,user_name, expo_name,iTransform);
    }
}
