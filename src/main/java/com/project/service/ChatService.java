package com.project.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Messages;
import com.project.repo.MessageRepo;
@Service
public class ChatService {
	@Autowired
    private MessageRepo messagesRepository;
	public List<Messages> getMessage(String roomId) {
		  return messagesRepository.findByRoomId(roomId);
	}
	


}
