package com.skylife.usermanagement.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skylife.usermanagement.dtos.CreateUserInputDTO;
import com.skylife.usermanagement.dtos.DeleteUserInputDTO;
import com.skylife.usermanagement.dtos.UpdateUserInputDTO;
import com.skylife.usermanagement.dtos.ViewUserDTO;
import com.skylife.usermanagement.exception.UserNotFoundException;
import com.skylife.usermanagement.models.User;
import com.skylife.usermanagement.repositories.UserRepo;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	UserRepo repo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<ViewUserDTO> listUsers() {
		List<User> allUsers = repo.findAll();
		return allUsers.stream().map(this::mapEntityToDTO).collect(Collectors.toList());

	}

	@Override
	public ViewUserDTO createUser(CreateUserInputDTO dto) {
		User user = dtoToEntity(dto);
		User newEntityCreated = repo.save(user);
		return mapEntityToDTO(newEntityCreated);
	}

	private ViewUserDTO mapEntityToDTO(User user) {
		ViewUserDTO userDto = new ViewUserDTO(user.getId(), user.getName(), user.getEmail());
		return userDto;
	}

	private User dtoToEntity(CreateUserInputDTO dto) {
		User user = new User();
		user.setName(dto.name());
		user.setEmail(dto.email());
		user.setPassword(passwordEncoder.encode(dto.password()));
		return user;
	}

	@Override
	public void deleteUser(DeleteUserInputDTO dto) throws UserNotFoundException {
		Optional<User> optionalUser = repo.findById(dto.id());
		User user = optionalUser
				.orElseThrow(() -> new UserNotFoundException("No User present with id " + dto.id() + " to delete"));
		repo.delete(user);
	}

	@Override
	public ViewUserDTO updateUser(UpdateUserInputDTO dto) throws UserNotFoundException {
		Optional<User> optionalUser = repo.findById(dto.id());
		User user = optionalUser
				.orElseThrow(() -> new UserNotFoundException("No User present with id " + dto.id() + " to update"));
		user.setName(dto.name());
		user.setEmail(dto.email());
		User updatedUser = repo.save(user);
		return mapEntityToDTO(updatedUser);

	}

	@Override
	public ViewUserDTO getUser(Long id) throws UserNotFoundException {
		Optional<User> optionalUser = repo.findById(id);
		User user = optionalUser
				.orElseThrow(() -> new UserNotFoundException("No User present with id " + id));
		return mapEntityToDTO(user);
	}

	@Override
	public void makeAdmin(Long id) throws UserNotFoundException {
		updateRole(id,"ROLE_ADMIN");
	}


	@Override
	public void makeUser(Long id) throws UserNotFoundException {
		updateRole(id,"ROLE_USER");
		
	}
	

	private void updateRole(Long id,String role) throws UserNotFoundException {
		Optional<User> optionalUser = repo.findById(id);
		User user = optionalUser
				.orElseThrow(() -> new UserNotFoundException("No User present with id " + id + " to update"));
		user.setRoles(role);
		repo.save(user);
	}

}
