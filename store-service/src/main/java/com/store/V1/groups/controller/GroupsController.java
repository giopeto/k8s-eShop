package com.store.V1.groups.controller;

import com.store.V1.groups.domain.Groups;
import com.store.V1.groups.service.GroupsService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.store.common.StoreConstants.STORE_BASE_URL;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(STORE_BASE_URL + "/groups")
@CrossOrigin
public class GroupsController {

	@NonNull private final GroupsService groupsService;

	@Autowired
	public GroupsController(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	@RequestMapping(method = POST)
	public Groups save(@RequestBody Groups groups) {
		return groupsService.save(groups);
	}

	@RequestMapping(value = "{id}", method = PUT)
	public Groups update(@PathVariable String id, @RequestBody Groups groups) {
		return groupsService.save(groups);
	}

	@RequestMapping(method = GET)
	public List<Groups> get() {
		return groupsService.get().getGroups();
	}

	@RequestMapping(value = "{id}", method = GET)
	public Groups getById(@PathVariable String id) {
		return groupsService.findById(id).get();
	}

	@RequestMapping(value = "{id}", method = DELETE)
	public void delete(@PathVariable String id) {
		groupsService.delete(id);
	}

}
