package com.project.model;

public class AuthRes {
	 private String userId;
	 private String username;
	 private String email;
	 private String token;
	 private long expires;
	 private String role;
	 public AuthRes(String token,String username,String role,long expiration) {
		 
	        this.username = username;
	        this.role=role;
	        this.token = token;
	        this.expires=expiration;
	 }
	public long getExpires() {
		return expires;
	}
	public void setExpires(long expires) {
		this.expires = expires;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getToken() {
		return token;
	}
	 
	 
}
