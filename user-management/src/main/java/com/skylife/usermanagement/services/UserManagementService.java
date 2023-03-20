package com.skylife.usermanagement.services;

import java.util.List;

import com.skylife.usermanagement.dtos.CreateUserInputDTO;
import com.skylife.usermanagement.dtos.DeleteUserInputDTO;
import com.skylife.usermanagement.dtos.UpdateUserInputDTO;
import com.skylife.usermanagement.dtos.ViewUserDTO;
import com.skylife.usermanagement.exception.UserNotFoundException;

public interface UserManagementService {
	
	List<ViewUserDTO> listUsers();

	ViewUserDTO createUser(CreateUserInputDTO dto);

	void deleteUser(DeleteUserInputDTO dto) throws UserNotFoundException;
	
	ViewUserDTO updateUser(UpdateUserInputDTO dto) throws UserNotFoundException;

	ViewUserDTO getUser(Long id) throws UserNotFoundException;

	void makeAdmin(Long id) throws UserNotFoundException;

	void makeUser(Long id) throws UserNotFoundException;
	
	

}
