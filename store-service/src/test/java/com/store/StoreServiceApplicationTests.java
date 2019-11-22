package com.store;

import com.store.v1.items.controller.ItemsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class StoreServiceApplicationTests {

    @Autowired
    private ItemsController itemsController;

    @Test
    public void contextLoads() {
        assertThat(itemsController).isNotNull();
    }

}
