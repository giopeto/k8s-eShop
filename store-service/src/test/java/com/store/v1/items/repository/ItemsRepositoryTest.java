package com.store.v1.items.repository;

import com.store.v1.groups.repository.GroupsRepository;
import com.store.v1.items.domain.Items;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.store.v1.items.utils.ItemsTestUtils.generateItems;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataMongoTest
@TestPropertySource("classpath:test.properties")
public class ItemsRepositoryTest {

    private static final int NUMBER_OF_TEST_RECORDS = 10;

    @Autowired
    private ItemsRepository itemsRepository;
    @MockBean
    private GroupsRepository groupsRepository;
    private List<Items> items;
    private String groupId;

    @Before
    public void setUp() throws Exception {
        groupId = randomUUID().toString();
        items = generateItems(NUMBER_OF_TEST_RECORDS, groupId);
        itemsRepository.saveAll(items);
        itemsRepository.findAll();
    }

    @After
    public void tearDown() throws Exception {
        itemsRepository.deleteAll();
    }

    @Test
    public void testFindById() throws Exception {
        Items expectedItems = items.get(0);
        Optional<Items> actualItems = itemsRepository.findById(expectedItems.getId());

        assertEquals(expectedItems, actualItems.get());
    }

    @Test
    public void testFindByGroupId() throws Exception {
        assertEquals(items, itemsRepository.findByGroupId(groupId));
    }

    @Test
    public void testDeleteById() throws Exception {
        Items itemsForDelete = items.get(0);
        itemsRepository.deleteById(itemsForDelete.getId());

        Optional<Items> deletedItems = itemsRepository.findAll().stream()
                .filter(item -> item.getId().equals(itemsForDelete.getId()))
                .findAny();

        assertFalse(deletedItems.isPresent());
    }
}