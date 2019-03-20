package com.napptilus.crewmanager.service;

import java.util.List;

import com.napptilus.crewmanager.exceptions.UserNotFoundException;
import com.napptilus.crewmanager.model.User;

public interface UserService {

	List<User> getUsers(String name, Integer index, Integer size);
	
	User getUser(int id) throws UserNotFoundException;
	
	User saveUser(User user);
	
	User updateUser(User user) throws UserNotFoundException;	
}
