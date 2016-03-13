package com.young.domain;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DoorGuard implements Serializable {
	private int id;
	private String card_id;
	private Timestamp time;
	private int openstate;
	private int agreestate;
	private int user_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getOpenstate() {
		return openstate;
	}

	public void setOpenstate(int openstate) {
		this.openstate = openstate;
	}

	public int getAgreestate() {
		return agreestate;
	}

	public void setAgreestate(int agreestate) {
		this.agreestate = agreestate;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
