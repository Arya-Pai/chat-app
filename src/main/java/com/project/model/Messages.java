package com.project.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection="messages")
public class Messages {
	@Id
	String id;
	
	@NotBlank
	String sender;
	
	@NotBlank
	String roomId;
	@NotBlank
	String content;
	
	
	private LocalDateTime sentAt = LocalDateTime.now();
	
	 public Messages() {} 
	public Messages(String roomId,String sender,String content) {
		this.roomId=roomId;
		this.sender=sender;
		this.content=content;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getRoomId() {
		return roomId;
	}


	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public LocalDateTime getSentAt() {
		return sentAt;
	}


	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
	
	
	
	
	
	
}
