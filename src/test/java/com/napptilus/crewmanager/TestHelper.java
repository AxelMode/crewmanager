package com.napptilus.crewmanager;

import java.util.ArrayList;
import java.util.List;

import com.napptilus.crewmanager.model.User;

public class TestHelper {

	public static User getTestUser1() {

		User user = new User();
		user.setName("Alex");
		user.setAge(39);
		user.setJob("Software Engineer");
		user.setDescription("Bla bla");
		user.setHeight(185f);
		user.setWeight(84.4f);
		return user;

	}

	public static User getTestUser2() {

		User user = new User();
		user.setName("Alex");
		user.setAge(25);
		user.setJob("Encofrador");
		user.setDescription("Descripcion encofrador");
		user.setHeight(169f);
		user.setWeight(70.4f);
		return user;

	}

	public static List<User> getEmptyUserList() {

		return new ArrayList<User>();

	}

	public static List<User> getUserList() {

		List<User> users = new ArrayList<User>();
		users.add(getTestUser1());
		return users;

	}

	public static List<User> getUserListWithMatches() {

		List<User> users = new ArrayList<User>();
		users.add(getTestUser1());
		users.add(getTestUser2());
		return users;
		
	}

}
