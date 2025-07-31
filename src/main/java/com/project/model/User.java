package com.project.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="users")
public class User {
	@Id
	private String id;
	private String username;
	private String email;
	private String role;
	private String password;
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public User() {}
	
	public User(String email ,String password) {
		this.email=email;
		this.password=password;
		
	}
	public User(String email ,String password,String username,String role) {
		this.email=email;
		this.password=password;
		this.username=username;
		this.role=role;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
