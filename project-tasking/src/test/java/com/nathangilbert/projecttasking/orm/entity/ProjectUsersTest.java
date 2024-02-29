package com.nathangilbert.projecttasking.orm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class ProjectUsersTest {

    @InjectMocks
    private ProjectUsers projectUsersAssociation;

    @BeforeEach
    public void setUp() throws Exception {
        long userId = 12;
        long projectId = 24;
        projectUsersAssociation = new ProjectUsers(userId, projectId);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(24, projectUsersAssociation.getProjectId());
        assertEquals(12, projectUsersAssociation.getUserId());
    }

    @Test
    public void testConstructorWithParameters() {
        long userId = 21;
        long projectId = 30;
        ProjectUsers projectUsersAssociation = new ProjectUsers(userId, projectId);

        assertEquals(30, projectUsersAssociation.getProjectId());
        assertEquals(21, projectUsersAssociation.getUserId());
    }


    @Test
    public void testToString() {
        String expectedToString = "ProjectUser {projectId='24', userId='12'}";
        assertEquals(expectedToString, projectUsersAssociation.toString());
    }
}