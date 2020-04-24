package com.abelurz.tutorial.aplicacion01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abelurz.tutorial.aplicacion01.Exception.CustomeFieldValidationException;
import com.abelurz.tutorial.aplicacion01.Exception.UsernameOrIdNotFound;
import com.abelurz.tutorial.aplicacion01.dto.ChangePasswordForm;
import com.abelurz.tutorial.aplicacion01.entity.User;
import com.abelurz.tutorial.aplicacion01.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Iterable<User> getAllUsers() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUserName(user.getUserName());
		if (userFound.isPresent()) {
			throw new CustomeFieldValidationException("Username no disponible", "userName");
		}
		return true;
		
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
		
		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new CustomeFieldValidationException("Password y Confirm password no son iguales","confirmPassword");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new CustomeFieldValidationException("Pass y Confirm password no son iguales", "password");
		}
		return true;
		
	}

	@Override
	public User createUser(User user) throws Exception {
		// TODO Auto-generated method stub
		if(checkUsernameAvailable(user) && checkPasswordValid(user)) {
			
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws UsernameOrIdNotFound {
		// TODO Auto-generated method stub
		return repository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("El Id del usuario no existe."));
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
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')") //revisar
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		User user = getUserById(id);
		repository.delete(user);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		// TODO Auto-generated method stub
		User user = getUserById(form.getId());
		if(!isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Current password invalid.");
		}
		if(user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("El nuevo password debe ser diferente al actual.");
		}
		if(!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("el nuevo pass y el confirm pass no coinciden.");
		}
		
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return repository.save(user);
	}
	
	private boolean isLoggedUserADMIN() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;
		Object roles = null;
		
		if(principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
			
			roles = loggedUser.getAuthorities().stream()
				.filter(x -> "ROLE_ADMIN".equals(x.getAuthority()))
				.findFirst().orElse(null);
		}
		return roles != null ? true : false;
	}
	
	public User getLoggedUser() throws Exception{
		//Obtener usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;
		
		//Verificar que ese objeto traido de la session  es el usuario
		if(principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = repository.findByUserName(loggedUser.getUsername()).orElseThrow(()-> new Exception("Problema obteniendo usuario de sesión"));
		return myUser;
		
	}
	
}