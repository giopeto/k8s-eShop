package com.edge;

import com.edge.authentication.v1.users.controller.UsersController;
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
public class EdgeServiceApplicationTests {

    @Autowired
    private UsersController usersController;

    @Test
    public void contextLoads() {
        assertThat(usersController).isNotNull();
    }

}
