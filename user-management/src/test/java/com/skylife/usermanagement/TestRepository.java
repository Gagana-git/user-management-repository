package com.skylife.usermanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.skylife.usermanagement.models.User;
import com.skylife.usermanagement.repositories.UserRepo;

@DataJpaTest
public class TestRepository {

	@Autowired
	UserRepo repo;

	private User user1;
	private User user2;
	private User user3;

	@BeforeEach
	public void init() {
		user1 = new User();
		user1.setName("user1");
		user1.setEmail("test@test.com");
		user1.setPassword("testpwd");

		user2 = new User();
		user2.setName("user2");
		user2.setEmail("test@test.com");
		user2.setPassword("testpwd");

		user3 = new User();
		user3.setName("user3");
		user3.setEmail("test@test.com");
		user3.setPassword("testpwd");

	}

	@Test
	public void testSave() {

		User newUser = repo.save(user1);
		assertNotNull(newUser);
		assertThat(newUser.getId()).isNotNull();

	}

	@Test
	public void testFindAll() {

		// Arrange
		repo.save(user1);
		repo.save(user2);
		repo.save(user3);

		// act
		List<User> allUsers = repo.findAll();

		// assert
		assertNotNull(allUsers);
		assertEquals(3, allUsers.size());

	}

	@Test
	public void testFindById() {

		// arrange
		User newUser = repo.save(user1);
		// act
		Optional<User> foundUser = repo.findById(newUser.getId());

		// assert
		assertNotNull(foundUser.get());
		assertEquals("user1", foundUser.get().getName());

	}

	@Test
	public void testUpdate() {
		//arrange
		User newUser = repo.save(user1);

		newUser.setName("testUpdated");
		//act
		User updatedUser = repo.save(newUser);

		// assert
		assertNotNull(updatedUser);
		assertEquals("testUpdated", updatedUser.getName());

	}

	@Test
	public void testDelete() {
		//arrange
		User newUser = repo.save(user2);
		Long id = newUser.getId();
		// act
		repo.delete(newUser);
		Optional<User> deletedUser = repo.findById(id);
		// assert
		assertThat(deletedUser).isEmpty();
	}

	@Test
	public void testfindByName() {
		// Arrange
		
		User newUser = repo.save(user3);

		// act
		Optional<User> fetchedUserByName = repo.findByName(newUser.getName());
		// assert
		assertNotNull(fetchedUserByName);
		assertEquals("user3", fetchedUserByName.get().getName());

	}

}
