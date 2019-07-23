package com.example.websocketdemo.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.websocketdemo.entity.MessageEntity;

public class Chatroom {
	private long id; // primary key
	private String name;
	private List<Message> messages = new ArrayList<Message>();
	
	public Chatroom()  {}
	public Chatroom(String userId, String name, List<Message> messages) {
		super();
		this.name = name;
		// this.messages = messages;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	/*
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	*/
	
}
