package com.nathangilbert.projecttasking.orm.dao;

import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.ProjectUsers;
import com.nathangilbert.projecttasking.orm.entity.User;
import com.nathangilbert.projecttasking.rest.exceptions.ProjectNotFoundException;
import com.nathangilbert.projecttasking.rest.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectUsersDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserDAO userDAO;

    @Mock
    TypedQuery<ProjectUsers> projectUserQuery;

    @Mock
    TypedQuery<Project> projectQuery;

    @InjectMocks
    private ProjectUsersDAO projectUsersDAO;

    @Test
    public void testProjectUserAssociationCreation() {
        long userId = 1;
        long projectId = 2;
        ProjectUsers projectUsers = new ProjectUsers(userId, projectId);
        
        projectUsersDAO.createProjectUserAssociation(projectUsers);
        
        verify(entityManager).persist(projectUsers);
    }

    @Test
    public void testGetProjectUserIds_ProjectFound() {
        long projectId = 2;

        List<ProjectUsers> projectUsers = new ArrayList<>();
        projectUsers.add(new ProjectUsers(1, projectId));
        projectUsers.add(new ProjectUsers(3, projectId));
        projectUsers.add(new ProjectUsers(5, projectId));

        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(3L);
        userIds.add(5L);

        when(entityManager.createQuery("FROM ProjectUsers WHERE projectId=:projectId", ProjectUsers.class)).thenReturn(
                projectUserQuery);
        when(projectUserQuery.getResultList()).thenReturn(projectUsers);

        List<Long> result = projectUsersDAO.getProjectUserIds(projectId);

        assertEquals(userIds, result);
    }

    @Test
    public void testGetProjectUserIds_ProjectNotFound() {
        long projectId = 2;

        List<ProjectUsers> projectUsers = new ArrayList<>();
        projectUsers.add(new ProjectUsers(1, projectId));
        projectUsers.add(new ProjectUsers(3, projectId));
        projectUsers.add(new ProjectUsers(5, projectId));

        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(3L);
        userIds.add(5L);

        when(entityManager.createQuery("FROM ProjectUsers WHERE projectId=:projectId", ProjectUsers.class)).thenReturn(
                projectUserQuery);
        when(projectUserQuery.getResultList()).thenReturn(null);

        List<Long> result = new ArrayList<>();
        try {
            result = projectUsersDAO.getProjectUserIds(projectId);
        } catch (ProjectNotFoundException exception) {
            assertTrue(exception.getMessage().contains("Project user associations not found for ProjectId:" + projectId));
        }
        assertNotEquals(userIds, result);
    }

    @Test
    public void testGetUsersProjects_ProjectFound() {
        long userId = 2;

        List<ProjectUsers> projectUsers = new ArrayList<>();
        projectUsers.add(new ProjectUsers(userId, 1));
        projectUsers.add(new ProjectUsers(userId, 2));
        projectUsers.add(new ProjectUsers(userId, 3));

        List<Project> projects = new ArrayList<>();

        Project project1 = new Project();
        Project project2 = new Project();
        Project project3 = new Project();

        project1.setProjectId(1);
        project2.setProjectId(3);
        project3.setProjectId(5);

        projects.add(project1);
        projects.add(project2);
        projects.add(project3);

        when(entityManager.createQuery("FROM ProjectUsers WHERE userId=:userId", ProjectUsers.class)).thenReturn(
                projectUserQuery);
        when(projectUserQuery.getResultList()).thenReturn(projectUsers);

        when(entityManager.createQuery("FROM Project WHERE projectId IN :projectIds",
                Project.class)).thenReturn(
                        projectQuery);
        when(projectQuery.getResultList()).thenReturn(projects);

        List<Project> result = projectUsersDAO.getUsersProjects(userId);

        assertEquals(projects, result);
    }

    @Test
    public void testGetUsersProjects_ProjectUserNotFound() {
        long userId = 2;

        List<ProjectUsers> projectUsers = new ArrayList<>();
        projectUsers.add(new ProjectUsers(userId, 1));
        projectUsers.add(new ProjectUsers(userId, 2));
        projectUsers.add(new ProjectUsers(userId, 3));

        List<Project> projects = new ArrayList<>();

        Project project1 = new Project();
        Project project2 = new Project();
        Project project3 = new Project();

        project1.setProjectId(1);
        project2.setProjectId(3);
        project3.setProjectId(5);

        projects.add(project1);
        projects.add(project2);
        projects.add(project3);

        when(entityManager.createQuery("FROM ProjectUsers WHERE userId=:userId", ProjectUsers.class)).thenReturn(
                projectUserQuery);
        when(projectUserQuery.getResultList()).thenReturn(null);

        List<Project> result = new ArrayList<>();

        try {
            result = projectUsersDAO.getUsersProjects(userId);
        } catch (ProjectNotFoundException exception) {
            assertTrue(
                    exception.getMessage().contains("Project user associations not found for UserId:" + userId));
        }
        assertNotEquals(projects, result);
    }

    @Test
    public void testGetUsersProjects_ProjectNotFound() {
        long userId = 2;

        List<ProjectUsers> projectUsers = new ArrayList<>();
        projectUsers.add(new ProjectUsers(userId, 1));
        projectUsers.add(new ProjectUsers(userId, 3));
        projectUsers.add(new ProjectUsers(userId, 5));

        List<Project> projects = new ArrayList<>();

        Project project1 = new Project();
        Project project2 = new Project();
        Project project3 = new Project();

        project1.setProjectId(1);
        project2.setProjectId(3);
        project3.setProjectId(5);

        projects.add(project1);
        projects.add(project2);
        projects.add(project3);

        when(entityManager.createQuery("FROM ProjectUsers WHERE userId=:userId", ProjectUsers.class)).thenReturn(
                projectUserQuery);
        when(projectUserQuery.getResultList()).thenReturn(projectUsers);

        when(entityManager.createQuery("FROM Project WHERE projectId IN :projectIds",
                Project.class)).thenReturn(
                        projectQuery);
        when(projectQuery.getResultList()).thenReturn(null);

        List<Project> result = new ArrayList<>();

        List<Long> projectIds = new ArrayList<>();
        projectIds.add(1L);
        projectIds.add(3L);
        projectIds.add(5L);

        try {
            result = projectUsersDAO.getUsersProjects(userId);
        } catch (ProjectNotFoundException exception) {
            assertTrue(
                    exception.getMessage().contains("Projects not found IDs:" + projectIds));
        }
        assertNotEquals(projects, result);
    }

    @Test
    public void testDeleteProjectUserAssociation_ResultsFound() {
        long projectId = 1;
        long userId = 3;

        ProjectUsers projectUser = new ProjectUsers(userId, projectId);

        when(entityManager.createQuery("FROM ProjectUsers WHERE projectId = :projectId AND userId = :userId",
                ProjectUsers.class)).thenReturn(
                projectUserQuery);
        when(projectUserQuery.getSingleResult()).thenReturn(projectUser);

        projectUsersDAO.deleteProjectUserAssociation(projectId, userId);

        verify(entityManager).remove(projectUser); 
    }

    @Test
    public void testDeleteProjectUserAssociation_NoResultsFound() {
        long projectId = 1;
        long userId = 3;

        ProjectUsers projectUser = new ProjectUsers(userId, projectId);

        when(entityManager.createQuery("FROM ProjectUsers WHERE projectId = :projectId AND userId = :userId",
                ProjectUsers.class)).thenReturn(
                        projectUserQuery);
        when(projectUserQuery.getSingleResult()).thenReturn(null);

        projectUsersDAO.deleteProjectUserAssociation(projectId, userId);

        try {
            projectUser = projectUserQuery.getSingleResult();
        } catch (NoResultException exception) {
            assertTrue(
                    exception.getMessage().contains("Project user associations not found for ProjectId:" + projectId + " UserId:" + userId));
        }

        verify(entityManager).remove(projectUser);
    }
}
