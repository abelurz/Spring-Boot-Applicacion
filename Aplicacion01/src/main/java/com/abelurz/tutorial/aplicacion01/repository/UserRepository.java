package com.abelurz.tutorial.aplicacion01.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abelurz.tutorial.aplicacion01.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	public Iterable<User> findAll(); 
	public Optional<User> findByUserName(String username);

}
