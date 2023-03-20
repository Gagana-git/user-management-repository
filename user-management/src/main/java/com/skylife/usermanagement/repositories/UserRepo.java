package com.skylife.usermanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skylife.usermanagement.models.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByName(String username);

}
