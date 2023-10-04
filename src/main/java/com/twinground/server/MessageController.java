package com.twinground.server;


import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final SimpUserRegistry userRegistry;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();
        System.out.println("Session Connected : " + sessionId);

        simpMessageSendingOperations.convertAndSendToUser(sessionId, "/queue/sessionId", sessionId);

    }

    // 사용자가 웹 소켓 연결을 끊으면 실행됨
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("sessionId Disconnected : " + sessionId);
    }
    @MessageMapping("/expo/capstone")
    public void connecting(SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        Connection connection = new Connection(sessionId, "dss");
        simpMessageSendingOperations.convertAndSend("/sub/expo/capstone", connection);
    }
    @MessageMapping("/expo/capstone/transform")
    public void broadcastMove(@Payload String jsonTransform){
        System.out.println(jsonTransform);
        simpMessageSendingOperations.convertAndSend("/sub/expo/capstone/transform", jsonTransform);
    }

    @MessageMapping("/expo/capstone/disconnection")
    public void broadcastDisconnection(@Payload String jsonDisconnection){
        simpMessageSendingOperations.convertAndSend("/sub/expo/capstone/disconnect", jsonDisconnection);
    }


}
