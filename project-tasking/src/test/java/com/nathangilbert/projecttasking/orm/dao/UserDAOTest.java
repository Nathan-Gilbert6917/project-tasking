package com.nathangilbert.projecttasking.orm.dao;

import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.User;
import com.nathangilbert.projecttasking.rest.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProjectUsersDAO projectUsersDAO;

    @InjectMocks
    private UserDAO userDAO;

    @Test
    public void testUserRegister() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        
        userDAO.register(user);

        verify(entityManager).persist(user);
    }

    @Test
    public void testFindById_UserFound() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        when(entityManager.find(User.class, userId)).thenReturn(user);

        User result = userDAO.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
    }

    @Test
    public void testFindById_UserNotFound() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        when(entityManager.find(User.class, userId)).thenReturn(null);

        try {
            userDAO.findById(userId);
        } catch (UserNotFoundException exception) {
            assertTrue(exception.getMessage().contains("User not found for ID:" + userId));
        }
    }

    @Test
    public void testUpdateUser_EmailAndUsernameUpdated() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setEmail("old@example.com");
        user.setUsername("oldUsername");

        User updatedUser = new User();
        updatedUser.setEmail("new@example.com");
        updatedUser.setUsername("newUsername");

        when(entityManager.find(User.class, userId)).thenReturn(user);

        userDAO.updateUser(userId, updatedUser);

        assertEquals(updatedUser.getEmail(), user.getEmail());
        assertEquals(updatedUser.getUsername(), user.getUsername());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setEmail("old@example.com");
        user.setUsername("oldUsername");

        User updatedUser = new User();
        updatedUser.setEmail("new@example.com");
        updatedUser.setUsername("newUsername");

        when(entityManager.find(User.class, userId)).thenReturn(null);

        try {
            userDAO.updateUser(userId, updatedUser);
        } catch (UserNotFoundException exception) {
            assertTrue(exception.getMessage().contains("User not found for ID:" + userId));
        }
    }

    @Test
    public void testDeleteUser_UserFound() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(entityManager.find(User.class, userId)).thenReturn(user);

        userDAO.deleteUser(userId);

        verify(entityManager).remove(user); 
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(entityManager.find(User.class, userId)).thenReturn(null);

        try {
            userDAO.deleteUser(userId);
        } catch (UserNotFoundException exception) {
            assertTrue(exception.getMessage().contains("User not found for ID:"+userId));
        }
    }

    @Test
    public void testGetUsersProjects() {
        long userId = 1L;
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());
        projects.add(new Project());

        when(projectUsersDAO.getUsersProjects(userId)).thenReturn(projects);

        List<Project> result = userDAO.getUsersProjects(userId);

        assertEquals(projects, result);
    }
}
