package com.napptilus.crewmanager.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.napptilus.crewmanager.exceptions.UserNotFoundException;
import com.napptilus.crewmanager.model.User;
import com.napptilus.crewmanager.repository.UserRepository;
import com.napptilus.crewmanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${message.user.not.found.error}")
	private String USER_NOT_FOUND;

	@Autowired
	public UserRepository userRepo;

	public List<User> getUsers(String name, Integer index, Integer size) {
		PageRequest pageRequest = PageRequest.of(index, size);

		if (StringUtils.isEmpty(name)) {
			return userRepo.findAll(pageRequest).getContent();
		}

		return userRepo.findByName(name, pageRequest);
	}

	public User getUser(int id) throws UserNotFoundException {

		return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));

	}

	public User saveUser(User user) {

		return userRepo.save(user);

	}

	public User updateUser(User user) throws UserNotFoundException {

		User recoveredUser = userRepo.findById(user.getId())
				.orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, user.getId())));

		BeanUtils.copyProperties(user, recoveredUser);

		return saveUser(recoveredUser);
	}
}
