package com.abelurz.tutorial.aplicacion01.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abelurz.tutorial.aplicacion01.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	public Iterable<User> findAll(); 
}
