package com.edge.authentication.V1.users.service;

import com.edge.authentication.V1.users.domain.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UsersService extends UserDetailsService {

	public void signIn(Users users);
	public Users signUp(Users users);
	public Users findOneByEmail(String email);

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}