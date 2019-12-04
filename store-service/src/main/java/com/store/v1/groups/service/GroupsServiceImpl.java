package com.store.v1.groups.service;

import com.store.v1.groups.domain.Groups;
import com.store.v1.groups.domain.GroupsList;
import com.store.v1.groups.repository.GroupsRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupsServiceImpl implements GroupsService {

    @NonNull
    private final GroupsRepository groupsRepository;

    public Groups save(Groups groups) {
        return groupsRepository.save(groups);
    }

    public GroupsList get() {
        return new GroupsList(groupsRepository.findAllByOrderByNameAsc());
    }

    public Optional<Groups> findById(String id) {
        return groupsRepository.findById(id);
    }

    public void delete(String id) {
        groupsRepository.deleteById(id);
    }

}
