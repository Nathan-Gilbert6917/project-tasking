package com.nathangilbert.projecttasking.orm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;

@ExtendWith(MockitoExtension.class)
public class ProjectTest {

    @InjectMocks
    private Project project;

    @Mock
    private User user;

    @Test
    public void testGettersAndSetters() {
        project.setProjectName("Test Project");
        project.setOwnerId(123);
        project.setDescription("Test Description");
        project.setAgingTaskDays(10);
        project.setProjectId(1);

        assertEquals("Test Project", project.getProjectName());
        assertEquals(123, project.getOwnerId());
        assertEquals("Test Description", project.getDescription());
        assertEquals(10, project.getAgingTaskDays());
        assertEquals(1, project.getProjectId());
    }

    @Test
    public void testConstructorWithParameters() {
        Project project = new Project("Test Project", 123, "Test Description", 10);

        assertEquals("Test Project", project.getProjectName());
        assertEquals(123, project.getOwnerId());
        assertEquals("Test Description", project.getDescription());
        assertEquals(10, project.getAgingTaskDays());
    }

    @Test
    public void testConstructorWithUser() {
        when(user.getUserId()).thenReturn(123L);
        Project project = new Project("Test Project", user, "Test Description", 10);

        assertEquals("Test Project", project.getProjectName());
        assertEquals(123, project.getOwnerId());
        assertEquals("Test Description", project.getDescription());
        assertEquals(10, project.getAgingTaskDays());
    }

    @Test
    public void testToString() {
        project.setProjectId(1);
        project.setProjectName("Test Project");
        project.setOwnerId(123);
        project.setDescription("Test Description");

        String expectedToString = "Project {projectId='1', projectName='Test Project', ownerId='123', description='Test Description'}";

        assertEquals(expectedToString, project.toString());
    }

    @Test
    public void testCreatedAtDefaultValue() {
        Project project = new Project();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // Allow a small tolerance for time differences
        long timeDifference = Math.abs(project.getCreatedAt().getTime() - currentTime.getTime());
        assertTrue(timeDifference < 1000); // Less than 1 second difference
    }
}
