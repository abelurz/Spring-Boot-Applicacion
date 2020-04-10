package com.abelurz.tutorial.aplicacion01.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abelurz.tutorial.aplicacion01.entity.Role;
import com.abelurz.tutorial.aplicacion01.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.abelurz.tutorial.aplicacion01.entity.User appUser = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Login username invalido"));
		
		Set grantList = new HashSet();
		for(Role role: appUser.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getDescription());
			grantList.add(grantedAuthority);
		}
		
		UserDetails user = (UserDetails) new User(username, appUser.getPassword(),grantList);
		return user;
	}

}
