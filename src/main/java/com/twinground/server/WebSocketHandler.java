package com.twinground.server;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twinground.model.packet.ConnectionBody;
import com.twinground.model.packet.InitBody;
import com.twinground.model.packet.Packet;
import com.twinground.model.packet.TransformBody;
import com.twinground.model.packet.transfrom.ITransform;
import com.twinground.model.packet.transfrom.Position;
import com.twinground.model.packet.transfrom.Quaternion;
import com.twinground.model.packet.transfrom.TransformData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashSet;


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
            ArrayList<TransformData> transforms = new ArrayList<>();
            String expo_name = bodyObject.getExpo_name();
            HashSet<SessionPacket> sessions = worldRepository.getWorld(expo_name).getSessions();
            for (SessionPacket sessionPacket : sessions) {
                if (!sessionPacket.getWebSocketSession().getId().equals(session.getId())) {
                    TransformBody tmp = (TransformBody) sessionPacket.getPacket().getBody();
                    transforms.add(tmp.toTransformData());
                }
            }
            bodyObject.setTransforms(transforms);
            Packet updatepacket = new Packet(id, bodyObject);
            TransformBody initTransformBody = initTransform(session.getId(), bodyObject.getExpo_name());
            Packet defaultPacket = new Packet(2, initTransformBody);
            SessionPacket sessionPacket= new SessionPacket(session, defaultPacket);
            String connectionJson = objectMapper.writeValueAsString(updatepacket);
            TextMessage textMessage = new TextMessage(connectionJson);
            session.sendMessage(textMessage);
            sessions.add(sessionPacket);
            ArrayList<TransformData> transforms1 = new ArrayList<>();
            transforms1.add(initTransformBody.toTransformData());
            bodyObject.setTransforms(transforms1);
            updatepacket.setBody(bodyObject);
            String aaaa = objectMapper.writeValueAsString(updatepacket);
            System.out.println(aaaa);
            worldRepository.getWorld(expo_name).send(updatepacket, objectMapper, session.getId());
        }
        //transform 변환하면 보내는 코드
        else if (id == 2) {
            TransformBody bodyObject = objectMapper.treeToValue(jsonNode.get("body"), TransformBody.class);
            Packet updatepacket = new Packet(id, bodyObject);
            String sessionId = session.getId();
            String expo_name = bodyObject.getExpo_name();
            HashSet<SessionPacket> sessions = worldRepository.getWorld(expo_name).getSessions();
            for (SessionPacket sessionPacket : sessions) {
                if (sessionPacket.getWebSocketSession().getId().equals(session.getId())) {
                    sessionPacket.setPacket(updatepacket);
                    break;
                }
            }
            worldRepository.getWorld(expo_name).send(updatepacket,objectMapper,sessionId);
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
        System.out.println(connectionJson);
        session.sendMessage(textMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        worldRepository.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }


    private TransformBody initTransform(String sessionId, String expo_name){
        Position position = new Position(0,0);
        Quaternion quaternion = new Quaternion(0,0);
        ITransform iTransform = new ITransform(position,quaternion,"idle");
        return new TransformBody(sessionId, expo_name,iTransform);
    }
}
