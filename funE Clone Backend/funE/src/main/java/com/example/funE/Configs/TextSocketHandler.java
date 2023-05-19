package com.example.funE.Configs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class TextSocketHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.logger(TextSocketHandler.class);

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        InetSocketAddress inetSocketAddress = session.getLocalAddress();
        logger.info(inetSocketAddress.getAddress().toString() + " " + inetSocketAddress.getPort());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        for(WebSocketSession webSocketSession : sessions) {
            if(!session.equals(webSocketSession)) {
                webSocketSession.sendMessage(message);
                log.info("Message: {}", message.getPayload());
            }
        }
    }
}
