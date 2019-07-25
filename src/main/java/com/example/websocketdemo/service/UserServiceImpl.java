package com.example.websocketdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.websocketdemo.domain.User;
import com.example.websocketdemo.entity.UserEntity;
import com.example.websocketdemo.exception.ResourceNotFoundException;
import com.example.websocketdemo.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired UserRepository repository;
	/*
	@Override
	public User getUserById(long id) {
		UserEntity userEntity = null;
		try {
			userEntity = repository.findById(id);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userEntity.buildDomain();
	}
	
	public List<User> getUsers(PageRequest pageRequest) {
		List<User> users = new ArrayList<User>();
		Page<UserEntity> entities = repository.findAll(pageRequest);
		for(UserEntity entity : entities) {
			User user = entity.buildDomain();
			users.add(user);
		}
		return users;
	}
	
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		List<UserEntity> entities = repository.findAll();
		for(UserEntity entity : entities) {
			User user = entity.buildDomain();
			users.add(user);
		}
		return users;
	}
	@Override
	public List<User> getUsersByName(String name) {
		List<User> users = new ArrayList<User>();
		List<UserEntity> entities = repository.findByNameOrderByIdAsc(name);
		for(UserEntity entity : entities) {
			User user = entity.buildDomain();
			users.add(user);
		}
		return users;
	}
	*/
	@Override
	public User getUserByUserId(String userId) {
		UserEntity userEntity = repository.findByUserId(userId);
		if(userEntity == null)
			return null;
		else
			return userEntity.buildDomain();
	}
	
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		UserEntity entity = new UserEntity();
		entity.buildEntity(user);
		if(repository.findByUserId(user.getUserId())==null)
			repository.save(entity);
	}
	@Override
	public void updateUser(User user) {
		UserEntity entity = new UserEntity();
		entity.buildEntity(user);
		repository.save(entity);
	}

	@Override
	public void deleteUser(User user) {
		UserEntity entity = new UserEntity();
		entity.buildEntity(user);
		repository.delete(entity);
	}
}