package com.napptilus.crewmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.napptilus.crewmanager.TestHelper;
import com.napptilus.crewmanager.exceptions.UserNotFoundException;
import com.napptilus.crewmanager.model.User;
import com.napptilus.crewmanager.repository.UserRepository;
import com.napptilus.crewmanager.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@MockBean
	private UserRepository repository;

	@Autowired
	private UserService userService;

	@Test(expected = UserNotFoundException.class)
	public void getUser_UserNotFound() {

		userService.getUser(1);

	}

	@Test
	public void getUser_UserFound() {

		Optional<User> testUser = Optional.of(TestHelper.getTestUser1());

		when(repository.findById(1)).thenReturn(testUser);

		User user = userService.getUser(1);

		assertNotNull(user);
		assertTrue(user.getName().equals("Alex"));
		assertEquals(user.getAge().intValue(), 39);
		assertEquals(user.getJob(), "Software Engineer");
		assertEquals(user.getDescription(), "Bla bla");
		assertTrue(user.getHeight() == 185f);
		assertTrue(user.getWeight() == 84.4f);

	}

	@Test
	public void getUsers_EmptyList() {

		PageRequest request = PageRequest.of(1, 10);
		when(repository.findAll(request)).thenReturn(new PageImpl<User>(TestHelper.getEmptyUserList()));

		List<User> users = userService.getUsers(null, 1, 10);
		assertTrue(users.size() == 0);

	}

	@Test
	public void getUsers_NotEmptyList() {

		PageRequest request = PageRequest.of(1, 10);

		when(repository.findAll(request)).thenReturn(new PageImpl<User>(TestHelper.getUserList()));

		List<User> users = userService.getUsers(null, 1, 10);
		assertTrue(users.size() == 1);

	}

	@Test
	public void saveUser_UserCreated() {

		User user = TestHelper.getTestUser1();

		when(repository.save(user)).thenReturn(user);

		assertNotNull(user);

	}

	@Test(expected = UserNotFoundException.class)
	public void updateUser_UserNotFound() {

		User testUser = TestHelper.getTestUser1();

		when(repository.findById(testUser.getId())).thenReturn(Optional.empty());

		userService.updateUser(testUser);

	}

	@Test
	public void updateUser_UserUpdated() {

		User sourceUser = TestHelper.getTestUser1();
		User targetUser = TestHelper.getTestUser1();
		targetUser.setName("Peter");

		when(repository.findById(sourceUser.getId())).thenReturn(Optional.of(sourceUser));
		when(repository.save(targetUser)).thenReturn(targetUser);

		User updatedUser = userService.updateUser(targetUser);

		assertTrue(updatedUser.getName().equals("Peter"));

	}
}
