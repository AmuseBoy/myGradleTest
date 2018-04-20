package com.liu.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.liu.person.user.User;
import com.liu.person.user.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String userid){
		User loginUser = userRepository.findOne(userid);
		if (loginUser == null) {
			throw new UsernameNotFoundException("Could not find user " + userid);
		}
		return new org.springframework.security.core.userdetails.User(loginUser.getId(), loginUser.getPassWord(), getAuthorities(loginUser));
	}
	
	
	public Collection<? extends GrantedAuthority> getAuthorities(User loginUser) {
		if(loginUser.getId().equals("admin")){
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		}
		return AuthorityUtils.createAuthorityList("ROLE_USER");
	}
}
