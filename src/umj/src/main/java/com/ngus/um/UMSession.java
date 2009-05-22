package com.ngus.um;

public class UMSession {
	private String sessionId = null;
	private User user = null;
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	UMSession(String sid, User user){
		this.sessionId = sid;
		this.user = user;
	}
	
}
