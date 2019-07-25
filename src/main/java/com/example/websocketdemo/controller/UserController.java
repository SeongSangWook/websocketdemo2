/*
package com.example.websocketdemo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.websocketdemo.domain.User;
import com.example.websocketdemo.service.UserService;
import com.example.websocketdemo.HttpSessionUtils;

@Controller
@RequestMapping("/")
public class UserController {
	@Autowired UserService userService; 
	// 의존성 주입(Dependency Injection)
	// @Component, @Controller, @Repository, @Service 표시된 클래스형 빈 객체를 스프링이 스캔하여 등록하고, @Autowired 등 요청시 주입 	
	
	
	@GetMapping("")
	public String index(@Valid User formUser, Model model) {
		return "index";
	}
	
	
	@PostMapping("")
	public String createUser(@Valid User formUser, Model model) {
		//userService.saveUser(formUser); 
		//model.addAttribute("user", formUser);
		System.out.println("aaa");
		System.out.println(formUser.getUserId());
		System.out.println(formUser.getUserPw());
		System.out.println(formUser.getName());
		return "index";
	}	
	
}
*/
