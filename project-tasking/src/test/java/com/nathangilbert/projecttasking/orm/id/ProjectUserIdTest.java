package com.nathangilbert.projecttasking.orm.id;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectUserIdTest {

    @InjectMocks
    private ProjectUserId projectUsersId;

    @BeforeEach
    public void setUp() throws Exception {
        long userId = 12;
        long projectId = 24;
        projectUsersId = new ProjectUserId(userId, projectId);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(24, projectUsersId.getProjectId());
        assertEquals(12, projectUsersId.getUserId());
    }

    @Test
    public void testConstructorWithParameters() {
        long userId = 21;
        long projectId = 30;
        ProjectUserId projectUsersId = new ProjectUserId(userId, projectId);

        assertEquals(30, projectUsersId.getProjectId());
        assertEquals(21, projectUsersId.getUserId());
    }
}