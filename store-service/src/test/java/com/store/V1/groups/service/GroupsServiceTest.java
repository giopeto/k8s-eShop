package com.store.V1.groups.service;

import com.store.V1.groups.domain.Groups;
import com.store.V1.groups.repository.GroupsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.store.V1.groups.utils.GroupsTestUtils.generateGroup;
import static com.store.V1.groups.utils.GroupsTestUtils.generateGroups;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GroupsServiceTest {

    private GroupsService groupsService;

    @Mock
    private GroupsRepository groupsRepository;

    private Groups groups;
    private String groupId;

    @Before
    public void setUp() {
        groupsService = new GroupsServiceImpl(groupsRepository);
        groups = generateGroup(null, Optional.empty());
        groupId = randomUUID().toString();
    }

    @Test
    public void testSave() {
        Groups savedGroups = generateGroup(groupId, Optional.of(groups.getName()));

        when(groupsRepository.save(groups)).thenReturn(savedGroups);

        Groups resultGroups = groupsService.save(groups);
        assertEquals(groupId, resultGroups.getId());
    }

    @Test
    public void testGet() {
        List<Groups> allGroupsOrderByName = generateGroups(2);

        when(groupsRepository.findAllByOrderByNameAsc()).thenReturn(allGroupsOrderByName);

        assertEquals(groupsService.get().getGroups(), allGroupsOrderByName);
    }

    @Test
    public void testGetById() {
        when(groupsRepository.findById(groupId)).thenReturn(Optional.of(groups));

        assertEquals(groupsService.findById(groupId).get(), groups);
    }

    @Test
    public void testDelete() {
        groupsRepository.deleteById(groupId);

        //assertFalse(groupsRepository.findById(groupId).get());
    }
}
