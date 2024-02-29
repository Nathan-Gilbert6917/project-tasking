package com.nathangilbert.projecttasking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.nathangilbert.projecttasking.orm.dao.ProjectDAO;
import com.nathangilbert.projecttasking.orm.dao.ProjectUsersDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectDAO projectDAO;

    @Mock
    private ProjectUsersDAO projectUsersDAO;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @Transactional
    public void testCreateProject() {
        Project project = new Project();
        projectService.createProject(project);
        verify(projectDAO, times(1)).createProject(project);
    }

    @Test
    public void testFindProjectById() {
        long projectId = 1L;
        Project project = new Project();
        when(projectDAO.findById(projectId)).thenReturn(project);

        Project foundProject = projectService.findById(projectId);
        assertEquals(project, foundProject);
    }

    @Test
    @Transactional
    public void testUpdateProject() {
        long projectId = 1L;
        Project project = new Project();
        projectService.updateProject(projectId, project);
        verify(projectDAO, times(1)).updateProject(projectId, project);
    }

    @Test
    @Transactional
    public void testDeleteProject() {
        long projectId = 1L;
        projectService.deleteProject(projectId);
        verify(projectDAO, times(1)).deleteProject(projectId);
    }

    @Test
    public void testGetOwnersProjects() {
        long ownerId = 1L;
        List<Project> projects = new ArrayList<>();
        when(projectDAO.getOwnersProjects(ownerId)).thenReturn(projects);

        List<Project> retrievedProjects = projectService.getOwnersProjects(ownerId);
        assertEquals(projects, retrievedProjects);
    }

    @Test
    public void testGetProjectUserIds() {
        long projectId = 1L;
        List<Long> userIds = new ArrayList<>();
        when(projectUsersDAO.getProjectUserIds(projectId)).thenReturn(userIds);

        List<Long> retrievedUserIdsList = projectService.getProjectUserIds(projectId);
        assertEquals(userIds, retrievedUserIdsList);
    }

    @Test
    @Transactional
    public void testJoinProject() {
        long projectId = 1;
        long userId = 2;

        try {
            projectService.joinProject(projectId, userId);
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    @Transactional
    public void testLeaveProject() {
        long projectId = 1L;
        long userId = 2L;
        projectService.leaveProject(projectId, userId);
        verify(projectUsersDAO, times(1)).deleteProjectUserAssociation(projectId, userId);
    }
}
