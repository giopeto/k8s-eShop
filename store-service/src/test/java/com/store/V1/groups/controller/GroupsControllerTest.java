package com.store.V1.groups.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.V1.groups.domain.Groups;
import com.store.V1.groups.domain.GroupsList;
import com.store.V1.groups.service.GroupsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.store.V1.groups.utils.GroupsTestUtils.generateGroup;
import static com.store.common.StoreConstants.STORE_BASE_URL;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupsController.class)
@RunWith(SpringRunner.class)
public class GroupsControllerTest {

	private static final String GROUP_URL = "/" + STORE_BASE_URL + "/groups";

	@Autowired private MockMvc mockMvc;
	@MockBean private GroupsService groupsService;

	private ObjectMapper jacksonObjectMapper;
	private Groups groups;
	private String id;

	@Before
	public void setUp() {

		jacksonObjectMapper = new ObjectMapper();
		id = randomUUID().toString();
		groups = generateGroup(id, Optional.empty());
	}

	@Test
	public void testSave() throws Exception {
		String newGroupName = "New Groups";
		Groups newGroups = generateGroup(null, Optional.of(newGroupName));
		Groups savedGroups = generateGroup(randomUUID().toString(), Optional.of(newGroupName));
		savedGroups.setName(newGroupName);

		when(groupsService.save(newGroups)).thenReturn(savedGroups);

		String request = jacksonObjectMapper.writeValueAsString(newGroups);
		String response = jacksonObjectMapper.writeValueAsString(savedGroups);

		mockMvc.perform(post(GROUP_URL)
			.content(request)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(response));
	}

	@Test
	public void testUpdate() throws Exception {
		groups.setName("Updated name");
		String request = jacksonObjectMapper.writeValueAsString(groups);

		mockMvc.perform(put(GROUP_URL + "/" + id)
			.content(request)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void testGet() throws Exception {
		when(groupsService.get()).thenReturn(new GroupsList(singletonList(groups)));

		String response = jacksonObjectMapper.writeValueAsString(groupsService.get().getGroups());

		mockMvc.perform(get(GROUP_URL))
			.andExpect(status().isOk())
			.andExpect(content().string(response));
	}

	@Test
	public void testGetById() throws Exception {
		when(groupsService.findById(id)).thenReturn(Optional.of(groups));

		String response = jacksonObjectMapper.writeValueAsString(groupsService.findById(id).get());

		mockMvc.perform(get(GROUP_URL + "/" + id))
			.andExpect(status().isOk())
			.andExpect(content().string(response));
	}

	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete(GROUP_URL + "/" + id)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}