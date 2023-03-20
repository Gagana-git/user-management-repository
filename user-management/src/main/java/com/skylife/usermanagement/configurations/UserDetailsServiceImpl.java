package com.skylife.usermanagement.configurations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.skylife.usermanagement.models.User;
import com.skylife.usermanagement.repositories.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.findByName(username);
		return user.map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("User Not found with username " + username));
	}

}
