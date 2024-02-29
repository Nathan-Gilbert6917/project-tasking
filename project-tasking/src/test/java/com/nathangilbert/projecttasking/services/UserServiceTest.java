package com.nathangilbert.projecttasking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nathangilbert.projecttasking.orm.dao.UserDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.User;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    @Transactional
    public void testRegisterUser() {
        User user = new User();
        userService.register(user);
        verify(userDAO, times(1)).register(user);
    }

    @Test
    public void testFindUserById() {
        long userId = 1L;
        User user = new User();
        when(userDAO.findById(userId)).thenReturn(user);

        User foundUser = userService.findById(userId);
        assertEquals(user, foundUser);
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        long userId = 1L;
        User user = new User();
        userService.updateUser(userId, user);
        verify(userDAO, times(1)).updateUser(userId, user);
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        long userId = 1L;
        userService.deleteUser(userId);
        verify(userDAO, times(1)).deleteUser(userId);
    }

    @Test
    public void testGetProjects() {
        long userId = 1L;
        List<Project> projects = new ArrayList<>();
        when(userDAO.getUsersProjects(userId)).thenReturn(projects);

        List<Project> retrievedProjects = userService.getProjects(userId);
        assertEquals(projects, retrievedProjects);
    }
}
