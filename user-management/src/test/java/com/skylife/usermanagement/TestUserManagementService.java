package com.skylife.usermanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.skylife.usermanagement.dtos.CreateUserInputDTO;
import com.skylife.usermanagement.dtos.ViewUserDTO;
import com.skylife.usermanagement.exception.UserNotFoundException;
import com.skylife.usermanagement.models.User;
import com.skylife.usermanagement.repositories.UserRepo;
import com.skylife.usermanagement.services.UserManagementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestUserManagementService {
	
	@InjectMocks
	UserManagementServiceImpl service;
	
	@Mock
	UserRepo repo;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	private User user1;
	
	@BeforeEach
	public void init() {
		user1 = new User();
		user1 = new User();
		user1.setId(1L);
		user1.setName("user1");
		user1.setEmail("test@test.com");
		user1.setPassword("testpwd");
	}
	
	@Test
	public void testCreateUser() {
		
		
		CreateUserInputDTO input = new CreateUserInputDTO(1L, "user1", "user@email.com", "testpwd");

		when(repo.save(any(User.class))).thenReturn(user1);
		when(passwordEncoder.encode(any(String.class))).thenReturn("$2a$10$xaLBX80mqOy5/S576cnB.eq6WbdSQHiKCrfROYyglam5D6lS4RqQe");
		
		ViewUserDTO newUser = service.createUser(input);
		
		assertNotNull(newUser);
		assertThat(newUser.name()).isEqualTo("user1");
	}
	@Test
	public void testReadUserById() throws UserNotFoundException {
		Optional<User> optionalUser = Optional.of(user1);
		when(repo.findById(any(Long.class))).thenReturn(optionalUser);
		
		ViewUserDTO user = service.getUser(1L);
		
		assertNotNull(user);
		assertThat(user.name()).isEqualTo("user1");
		
	}
	
	

}
