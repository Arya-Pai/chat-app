package com.project.model;

import java.time.LocalDateTime;
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
	private Set<String> members;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	public ChatRoom() {}
	public ChatRoom(String name,boolean isGroup,Set<String> members) {
		this.name=name;
		this.isGroup=isGroup;
		this.members=members;
	}
	public ChatRoom(String name,Set<String> members) {
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
	public Set<String> getMembers() {
		return members;
	}
	public void setMembers(Set<String> members) {
		this.members = members;
	}
	
	
	
}
