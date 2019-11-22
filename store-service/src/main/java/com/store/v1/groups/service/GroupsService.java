package com.store.v1.groups.service;

import com.store.v1.groups.domain.Groups;
import com.store.v1.groups.domain.GroupsList;

import java.util.Optional;

public interface GroupsService {

    public Groups save(Groups groups);

    public GroupsList get();

    public Optional<Groups> findById(String id);

    public void delete(String id);
}
