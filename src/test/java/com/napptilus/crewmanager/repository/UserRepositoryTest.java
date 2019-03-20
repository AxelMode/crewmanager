package com.napptilus.crewmanager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.napptilus.crewmanager.TestHelper;
import com.napptilus.crewmanager.model.User;
import com.napptilus.crewmanager.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void save_User() {

		User savedUser = repository.save(TestHelper.getTestUser1());

		assertNotNull(savedUser.getId());
		assertTrue(savedUser.getName().equals("Alex"));
		assertEquals(savedUser.getAge().intValue(), 39);
		assertEquals(savedUser.getJob(), "Software Engineer");
		assertEquals(savedUser.getDescription(), "Bla bla");
		assertTrue(savedUser.getHeight() == 185f);
		assertTrue(savedUser.getWeight() == 84.4f);

	}

	@Test
	public void save_UserNotExists() {

		Optional<User> savedUser = repository.findById(1);

		assertFalse(savedUser.isPresent());

	}

	@Test
	public void findById_UserLocated() {

		User savedUser = repository.save(TestHelper.getTestUser1());
		Optional<User> recoveredUser = repository.findById(savedUser.getId());

		assertTrue(recoveredUser.isPresent());
		assertNotNull(recoveredUser.get().getId());

	}
	
	@Test
	public void findByName_NotFound() {
		
		List<User> users = repository.findByName("Alex", null);
		assertTrue(users.size()==0);
		
	}
	
	@Test
	public void findByName_UsersFound() {
		
		repository.save(TestHelper.getTestUser1());
		repository.save(TestHelper.getTestUser2());
		
		List<User> users = repository.findByName("Alex", null);
		
		assertTrue(users.size()==2);
		
	}

	@Test
	public void findAll_EmptyList() {

		List<User> users = repository.findAll();
		assertTrue(users.isEmpty());

	}

	@Test
	public void findAll_NotEmptyList() {

		List<User> users = repository.findAll();
		assertTrue(users.size() == 0);

		repository.save(TestHelper.getTestUser1());
		users = repository.findAll();
		assertTrue(users.size() == 1);

	}
}