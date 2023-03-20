package com.skylife.usermanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skylife.usermanagement.dtos.CreateUserInputDTO;
import com.skylife.usermanagement.dtos.DeleteUserInputDTO;
import com.skylife.usermanagement.dtos.UpdateUserInputDTO;
import com.skylife.usermanagement.dtos.ViewUserDTO;
import com.skylife.usermanagement.exception.UserNotFoundException;
import com.skylife.usermanagement.services.UserManagementService;
import com.skylife.usermanagement.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping(AppConstants.API)
public class UserManagementController {

	@Autowired
	UserManagementService UserManagementService;

	@PostMapping(AppConstants.USERS)
	public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserInputDTO dto) {
		ViewUserDTO addedUser = UserManagementService.createUser(dto);
		ResponseEntity<Object> response = new ResponseEntity<Object>(addedUser, HttpStatus.CREATED);
		return response;
	}

	@GetMapping(AppConstants.USERBYID)
	public ResponseEntity<Object> readUser(@PathVariable("id") Long id) throws UserNotFoundException {
		ViewUserDTO user = UserManagementService.getUser(id);
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}

	@PutMapping(AppConstants.USERS)
	public ResponseEntity<Object> updateUser(@RequestBody @Valid UpdateUserInputDTO dto) throws UserNotFoundException {
		ViewUserDTO updatedUser = UserManagementService.updateUser(dto);
		ResponseEntity<Object> response = new ResponseEntity<Object>(updatedUser, HttpStatus.OK);
		return response;
	}

	@DeleteMapping(AppConstants.USERS)
	@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
	public ResponseEntity<Object> deleteUser(@RequestBody @Valid DeleteUserInputDTO dto) throws UserNotFoundException {
		UserManagementService.deleteUser(dto);
		ResponseEntity<Object> response = new ResponseEntity<Object>("Record deleted", HttpStatus.OK);
		return response;
	}

	@GetMapping(AppConstants.USERS)
	@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN') or hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Object> listUsers() {
		ResponseEntity<Object> response;
		List<ViewUserDTO> usersList = UserManagementService.listUsers();
		if (usersList.isEmpty()) {
			response = new ResponseEntity<Object>("No users", HttpStatus.OK);
		} else {
			response = new ResponseEntity<Object>(usersList, HttpStatus.OK);
		}
		return response;
	}

	@PutMapping(AppConstants.ROLEUPDATE_ADMIN)
	@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
	public ResponseEntity<Object> makeAdmin(@PathVariable("id") Long id) throws UserNotFoundException {
		UserManagementService.makeAdmin(id);
		return new ResponseEntity<Object>("User with id " + id + " provided with admin access", HttpStatus.OK);
	}

	@PutMapping(AppConstants.ROLEUPDATE_USER)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Object> makeUser(@PathVariable("id") Long id) throws UserNotFoundException {
		UserManagementService.makeUser(id);
		return new ResponseEntity<Object>("User with id " + id + " provided with user access", HttpStatus.OK);
	}
	
	@GetMapping(AppConstants.ROLE_USER_ENDPOINT)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Object> userEndPoint(){
		return new ResponseEntity<Object>("This endpoint can be accessed only by users with ROLE_USER", HttpStatus.OK);
	}
	
	@GetMapping(AppConstants.ROLE_ADMIN_ENDPOINT)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Object> adminEndPoint(){
		return new ResponseEntity<Object>("This endpoint can be accessed only by users with ROLE_ADMIN", HttpStatus.OK);
	}
	
	@GetMapping(AppConstants.ROLE_SUPER_ADMIN_ENDPOINT)
	@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
	public ResponseEntity<Object> superAdminEndPoint(){
		return new ResponseEntity<Object>("This endpoint can be accessed only by users with ROLE_SUPER_ADMIN", HttpStatus.OK);
	}

}
