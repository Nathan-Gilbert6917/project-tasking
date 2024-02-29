package com.nathangilbert.projecttasking.orm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Timestamp;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class UserTest {

    @InjectMocks
    private User user;

    @Test
    public void testGettersAndSetters() {
        user.setUsername("TestUser");
        user.setEmail("test@project.com");
        user.setUserId(10L);

        assertEquals("TestUser", user.getUsername());
        assertEquals("test@project.com", user.getEmail());
        assertEquals(10, user.getUserId());
    }

    @Test
    public void testConstructorWithParameters() {
        User user = new User("TestUser", "test@project.com");

        assertEquals("TestUser", user.getUsername());
        assertEquals("test@project.com", user.getEmail());
    }

    @Test
    public void testToString() {
        user.setUserId(1);
        user.setUsername("TestUser");
        user.setEmail("test@project.com");

        String expectedToString = "User {userId='1', username='TestUser', email='test@project.com'}";

        assertEquals(expectedToString, user.toString());
    }

    @Test
    public void testCreatedAtDefaultValue() {
        User user = new User();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // Allow a small tolerance for time differences
        long timeDifference = Math.abs(user.getCreatedAtTime().getTime() - currentTime.getTime());
        assertTrue(timeDifference < 1000); // Less than 1 second difference
    }
}
