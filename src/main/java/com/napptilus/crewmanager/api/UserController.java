package com.napptilus.crewmanager.api;

import java.lang.reflect.Type;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.napptilus.crewmanager.dto.UserDto;
import com.napptilus.crewmanager.exceptions.UserNotFoundException;
import com.napptilus.crewmanager.model.User;
import com.napptilus.crewmanager.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/")
public class UserController {

	@Autowired
	public UserService service;

	@Autowired
	public ModelMapper modelMapper;

	@GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getUsers(@RequestParam(required = false) String name,
			@RequestParam(required = false, defaultValue = "0") Integer index,
			@RequestParam(required = false, defaultValue = "25") Integer size) {

		return convertToDtoList(service.getUsers(name, index, size));

	}

	@GetMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUser(@PathVariable Integer id) throws UserNotFoundException {

		return convertToDto(service.getUser(id));

	}

	@PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto saveUser(@RequestBody @Valid UserDto userDto) {

		return convertToDto(service.saveUser(convertToEntity(userDto)));

	}

	@PutMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto updateUser(@RequestBody @Valid UserDto userDto) throws UserNotFoundException {

		return convertToDto(service.updateUser(convertToEntity(userDto)));

	}

	private UserDto convertToDto(User user) {

		return modelMapper.map(user, UserDto.class);

	}

	private User convertToEntity(UserDto userDto) {

		return modelMapper.map(userDto, User.class);

	}

	private List<UserDto> convertToDtoList(List<User> users) {

		Type listType = new TypeToken<List<UserDto>>() {
		}.getType();
		return modelMapper.map(users, listType);

	}

}