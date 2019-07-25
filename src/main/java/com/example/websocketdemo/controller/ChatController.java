package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.User;
import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.service.UserService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
	/*
	 * Client -> Server Stomp 요청시
	 * MessageMapping 사용해서 받는다.
	 * 어떤 방에 구독했는지 / 어떤 방에 말했는지가 SendTo경로에 드러남.
	 */
	
	@Autowired UserService userService; 
	// @Autowired MessageService userService; 
	// @Autowired ChatroomService userService; 
	
    @MessageMapping("/chat.sendMessage") // /app/chat.sendMessage
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser") // /app/chat.addUser
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
    @MessageMapping("/registerUser")
    @SendTo("/topic/public")
    public User registerUser(@Payload User user) {
        // Add username in web socket session
        userService.saveUser(user);
        return user;
    }
    
    @MessageMapping("/loginUser")
    @SendTo("/topic/public")
    public User loginUser(@Payload User user, HttpSession session) {
		System.out.println("asdf");
		return user;
    	/*
    	User sessionUser = userService.getUserByUserId(user.getUserId());
		System.out.println(sessionUser.getUserId());
		System.out.println(sessionUser.getUserPw());
		System.out.println(sessionUser.getName());
		
		if(sessionUser == null) {
			System.out.println("id error : ");
		}
		else if(!sessionUser.getUserPw().equals(user.getUserPw())) {
			System.out.println("pw error : ");
		}
		session.setAttribute("user", sessionUser);
		*/
		
    }
}