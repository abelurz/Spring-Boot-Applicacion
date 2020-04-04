package com.abelurz.tutorial.aplicacion01.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abelurz.tutorial.aplicacion01.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
}
