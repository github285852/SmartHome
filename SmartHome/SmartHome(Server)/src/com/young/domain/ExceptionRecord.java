package com.young.domain;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class ExceptionRecord implements Serializable {
	private int id;
	private String event;
	private Timestamp time;
	private int user_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
