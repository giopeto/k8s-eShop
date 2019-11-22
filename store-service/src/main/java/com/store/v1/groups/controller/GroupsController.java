package com.store.v1.groups.controller;

import com.store.v1.groups.domain.Groups;
import com.store.v1.groups.service.GroupsService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.store.common.StoreConstants.STORE_BASE_URL;

@RestController
@RequestMapping(STORE_BASE_URL + "/groups")
public class GroupsController {

    @NonNull
    private final GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @PostMapping
    public Groups save(@RequestBody Groups groups) {
        return groupsService.save(groups);
    }

    @PutMapping(value = "{id}")
    public Groups update(@PathVariable String id, @RequestBody Groups groups) {
        return groupsService.save(groups);
    }

    @GetMapping
    public List<Groups> get() {
        return groupsService.get().getGroups();
    }

    @GetMapping(value = "{id}")
    public Groups getById(@PathVariable String id) {
        return groupsService.findById(id).get();
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable String id) {
        groupsService.delete(id);
    }

}
