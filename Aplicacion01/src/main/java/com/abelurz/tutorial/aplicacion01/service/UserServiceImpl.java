package com.abelurz.tutorial.aplicacion01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abelurz.tutorial.aplicacion01.entity.User;
import com.abelurz.tutorial.aplicacion01.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Override
	public Iterable<User> getAllUsers() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUserName(user.getUserName());
		if (userFound.isPresent()) {
			throw new Exception("Username no disponible");
		}
		return true;
		
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("La clave no es ingual");
		}
		return true;
		
	}

	@Override
	public User createUser(User user) throws Exception {
		// TODO Auto-generated method stub
		if(checkUsernameAvailable(user) && checkPasswordValid(user)) {
			user = repository.save(user);
		}
		return user;
	}
	
}