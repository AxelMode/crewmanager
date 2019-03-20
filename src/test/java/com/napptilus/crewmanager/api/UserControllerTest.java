package com.napptilus.crewmanager.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.napptilus.crewmanager.TestHelper;
import com.napptilus.crewmanager.exceptions.UserNotFoundException;
import com.napptilus.crewmanager.model.User;
import com.napptilus.crewmanager.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private static final String URI_USERS = "/api/v1/users";

	@Before
	public void initialize() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getUsers_EmptyList() throws Exception {

		when(userService.getUsers(null, null, null)).thenReturn(new ArrayList<User>());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS)).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(new ArrayList<User>()), mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void getUsers_NotEmptyList() throws Exception {

		when(userService.getUsers(isNull(), anyInt(), anyInt())).thenReturn(TestHelper.getUserList());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS)).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(TestHelper.getUserList()), mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void getUser_UserFound() throws Exception {

		when(userService.getUser(1)).thenReturn(TestHelper.getTestUser1());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS + "/1")).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(TestHelper.getTestUser1()), mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void getUser_UserNotFound() throws Exception {

		when(userService.getUser(1)).thenThrow(new UserNotFoundException(""));
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS + "/1")).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 404);
	}

	public void saveUser_UserCreated() throws Exception {

		String inputJson = mapToJson(TestHelper.getTestUser1());

		when(userService.saveUser(TestHelper.getTestUser1())).thenReturn(TestHelper.getTestUser1());

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(URI_USERS).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 201);
		assertEquals(mapToJson(TestHelper.getTestUser1()), mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void updateUser_UserNotFound() throws Exception {

		String inputJson = mapToJson(TestHelper.getTestUser1());

		when(userService.updateUser(TestHelper.getTestUser1())).thenThrow(new UserNotFoundException(""));

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.put(URI_USERS).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 404);

	}

	@Test
	public void updateUser_UserUpdated() throws Exception {

		String inputJson = mapToJson(TestHelper.getTestUser1());

		when(userService.updateUser(TestHelper.getTestUser1())).thenReturn(TestHelper.getTestUser1());

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.put(URI_USERS).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(TestHelper.getTestUser1()), mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void getUserByName_EmptyUsersList() throws Exception {

		when(userService.getUsers("Peter", null, null)).thenReturn(new ArrayList<User>());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS + "?name=Peter")).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(new ArrayList<User>()), mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void getUserByName_UserFound() throws Exception {

		when(userService.getUsers("Alex", 0, 25)).thenReturn(TestHelper.getUserList());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS + "?name=Alex")).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(TestHelper.getUserList()), mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void getUserByName_AllMatchesByDefault() throws Exception {

		when(userService.getUsers("Alex", 0, 25)).thenReturn(TestHelper.getUserListWithMatches());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS + "?name=Alex")).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(TestHelper.getUserListWithMatches()), mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void getUserByName_SubsetOfMatches() throws Exception {

		when(userService.getUsers("Alex", 0, 1)).thenReturn(TestHelper.getUserList());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_USERS + "?name=Alex&index=0&size=1"))
				.andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);
		assertEquals(mapToJson(TestHelper.getUserList()), mvcResult.getResponse().getContentAsString());

	}

	protected String mapToJson(Object obj) throws JsonProcessingException {

		return new ObjectMapper().writeValueAsString(obj);

	}
}