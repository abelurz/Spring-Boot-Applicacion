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
		
		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("Password y Confirm password no son iguales");
		}
		
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

	@Override
	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		// TODO Auto-generated method stub
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		repository.save(toUser);
		return repository.save(toUser);
	}

	private void mapUser(User from, User to) {
		// TODO Auto-generated method stub
		to.setUserName(from.getUserName());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
		
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		// TODO Auto-generated method stub
		User user = getUserById(id);
		repository.delete(user);
	}
	
}