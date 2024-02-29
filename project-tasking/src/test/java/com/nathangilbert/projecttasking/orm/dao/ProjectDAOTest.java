package com.nathangilbert.projecttasking.orm.dao;

import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.rest.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class ProjectDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProjectUsersDAO projectUsersDAO;

    @Mock
    TypedQuery<Project> projectQuery;

    @InjectMocks
    private ProjectDAO projectDAO;

    @Test
    public void testProjectCreation() {
        long projectId = 10;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName("Project A");
        project.setDescription("Test description");
        project.setAgingTaskDays(10);
        
        projectDAO.createProject(project);
        
        verify(entityManager).persist(project);
    }

    @Test
    public void testFindById_ProjectFound() {
        long projectId = 10;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName("Project A");
        project.setDescription("Test description");
        project.setAgingTaskDays(10);

        when(entityManager.find(Project.class, projectId)).thenReturn(project);

        Project result = projectDAO.findById(projectId);

        assertNotNull(result);
        assertEquals(projectId, result.getProjectId());
    }

    @Test
    public void testFindById_ProjectNotFound() {
        long projectId = 10;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName("Project A");
        project.setDescription("Test description");
        project.setAgingTaskDays(10);

        when(entityManager.find(Project.class, projectId)).thenReturn(null);

        try {
            projectDAO.findById(projectId);
        } catch (ProjectNotFoundException exception) {
            assertTrue(exception.getMessage().contains("Project not found for ID:" + projectId));
        }
    }

    @Test
    public void testUpdateProject_ProjectNameAndDescriptionAndAgingTaskDaysUpdated() {
        long projectId = 10;
        Project project = new Project();
        project.setProjectName("Project A");
        project.setDescription("Test description");
        project.setAgingTaskDays(10);

        Project updatedProject = new Project();
        updatedProject.setProjectName("Project B");
        updatedProject.setDescription("New Test description");
        updatedProject.setAgingTaskDays(15);

        when(entityManager.find(Project.class, projectId)).thenReturn(project);

        projectDAO.updateProject(projectId, updatedProject);

        assertEquals(updatedProject.getProjectName(), project.getProjectName());
        assertEquals(updatedProject.getDescription(), project.getDescription());
        assertEquals(updatedProject.getAgingTaskDays(), project.getAgingTaskDays());
    }

    @Test
    public void testUpdateProject_ProjectNotFound() {
        long projectId = 10;
        Project project = new Project();
        project.setProjectName("Project A");
        project.setDescription("Test description");
        project.setAgingTaskDays(10);

        Project updatedProject = new Project();
        updatedProject.setProjectName("Project B");
        updatedProject.setDescription("New Test description");
        updatedProject.setAgingTaskDays(15);

        when(entityManager.find(Project.class, projectId)).thenReturn(null);
        
        try {
            projectDAO.updateProject(projectId, updatedProject);
        } catch (ProjectNotFoundException exception) {
            assertTrue(exception.getMessage().contains("Project not found for ID:" + projectId));
        }
        verifyNoMoreInteractions(entityManager);
        assertNotEquals(updatedProject.getProjectName(), project.getProjectName());
        assertNotEquals(updatedProject.getDescription(), project.getDescription());
        assertNotEquals(updatedProject.getAgingTaskDays(), project.getAgingTaskDays());
    }

    @Test
    public void testDeleteProject_UserFound() {
        long projectId = 10;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName("Project A");
        project.setDescription("Test description");
        project.setAgingTaskDays(10);

        when(entityManager.find(Project.class, projectId)).thenReturn(project);

        projectDAO.deleteProject(projectId);

        verify(entityManager).remove(project); 
    }

    @Test
    public void testDeleteProject_UserNotFound() {
        long projectId = 10;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName("Project A");
        project.setDescription("Test description");
        project.setAgingTaskDays(10);

        when(entityManager.find(Project.class, projectId)).thenReturn(null);

        try {
            projectDAO.deleteProject(projectId);
        } catch (ProjectNotFoundException exception) {
            assertTrue(exception.getMessage().contains("Project not found for ID:"+projectId));
        }
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testGetOwnersProjects() {
        long projectId = 1;
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());
        projects.add(new Project());
        
        when(entityManager.createQuery("FROM Project WHERE ownerId=:ownerId", Project.class)).thenReturn(projectQuery);
        when(projectQuery.getResultList()).thenReturn(projects);

        List<Project> result = projectDAO.getOwnersProjects(projectId);

        assertEquals(projects, result);
    }

    @Test
    public void testGetOwnersProjects_ProjectNotFound() {
        long projectId = 1;
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());
        projects.add(new Project());

        when(entityManager.createQuery("FROM Project WHERE ownerId=:ownerId", Project.class)).thenReturn(projectQuery);
        when(projectQuery.getResultList()).thenReturn(null);

        List<Project> result = new ArrayList<>();
        try {
            result = projectDAO.getOwnersProjects(projectId);
        } catch (ProjectNotFoundException exception) {
            assertTrue(exception.getMessage().contains("No projects owned by UserID:" + projectId));
        }
        verifyNoMoreInteractions(entityManager);
        assertNotEquals(projects, result);
    }
}
