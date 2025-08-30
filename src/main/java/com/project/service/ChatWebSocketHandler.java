package com.project.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.Messages;
import com.project.repo.MessageRepo;
import com.project.util.auth;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	
	private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
	private final MessageRepo messageRepo;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	public ChatWebSocketHandler(MessageRepo messageRepo) {
		System.out.println("Chat Handler initialised");
		this.messageRepo=messageRepo;
	}
	private final auth authentication=new auth();

    
	
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("After connection called");
		String roomId = getRoomId(session);
		String jwtToken = getJwtToken(session);
		try {
			if(jwtToken==null || !authentication.validateToken(jwtToken)) {
				System.out.println("Invalid or missing JWT for room: " + roomId);
		        session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Invalid token"));
		        return;
			}
			chatRooms.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        chatRooms.get(roomId).add(session);
        System.out.println("User joined room: " + roomId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	String roomId = getRoomId(session);
        String payload = message.getPayload();
        try {
        	Messages chatMessage = objectMapper.readValue(payload, Messages.class);
            chatMessage.setRoomId(roomId);
            chatMessage.setSentAt(LocalDateTime.now());
            messageRepo.save(chatMessage);
            
            broadcast(roomId, chatMessage);
        }
        catch(Exception e) {
        	 session.sendMessage(new TextMessage("{\"error\":\"Invalid message format\"}"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	String roomId = getRoomId(session);
        Set<WebSocketSession> sessions = chatRooms.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                chatRooms.remove(roomId);
            }
        }
        System.out.println("User left room: " + roomId);
    }
    private String getRoomId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=", 2);
                if (pair.length == 2 && pair[0].equals("roomId")) {
                	System.out.println(pair[1]);
                    return pair[1];
                }
            }
        }
        throw new IllegalArgumentException("Invalid room ID in session URI");
    }
    
    
    private String getJwtToken(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=", 2);
                if (pair.length == 2 && pair[0].equals("token")) {
                    return pair[1];
                }
            }
        }
        return null;
    }
    private void broadcast(String roomId, Messages chatMessage) throws Exception {
        String json = objectMapper.writeValueAsString(chatMessage);
        for (WebSocketSession s : chatRooms.getOrDefault(roomId, Collections.emptySet())) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(json));
            }
        }
    }
}
