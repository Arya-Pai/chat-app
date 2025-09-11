package com.project.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
@Document(collection="Chat-Room")

public class ChatRoom {
	@Id
	private String id;
	@NotBlank(message = "Name is required")
	@Indexed(unique = true)
	private String name;
	private boolean isGroup;
	@NotEmpty(message = "Members list cannot be empty")
	@Size(min = 2)
	private HashMap<String,String> members;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	public ChatRoom() {}
	public ChatRoom(String name,boolean isGroup,HashMap<String,String> members) {
		this.name=name;
		this.isGroup=isGroup;
		this.members=members;
	}
	public ChatRoom(String name,HashMap<String,String> members) {
		this.name=name;
		this.members=members;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public HashMap<String,String> getMembers() {
		return members;
	}
	public void setMembers(HashMap<String,String> members) {
		this.members = members;
	}
	
	public String getMemberName(String email1) {
		String email12 = email1;
		return members.get(email12);
	}
	
	public static  String encodeEmail(String email) {
	    return email.replace(".", "_");
	}

	public static String decodeEmail(String encoded) {
	    return encoded.replace("_", ".");
	}

	
	
	
}
