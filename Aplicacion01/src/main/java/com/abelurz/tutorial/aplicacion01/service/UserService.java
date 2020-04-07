package com.abelurz.tutorial.aplicacion01.service;


import com.abelurz.tutorial.aplicacion01.dto.ChangePasswordForm;
import com.abelurz.tutorial.aplicacion01.entity.User;

public interface UserService {
	public Iterable<User> getAllUsers();
	public User createUser(User user) throws Exception;
	public User getUserById(Long id) throws Exception;
	public User updateUser(User user) throws Exception;
	public void deleteUser(Long id) throws Exception;
	public User changePassword(ChangePasswordForm form) throws Exception;
}
