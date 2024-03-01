package com.nathangilbert.projecttasking.orm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.rest.exceptions.UserNotFoundException;
import com.nathangilbert.projecttasking.orm.dao.interfaces.IUserDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.User;

import jakarta.persistence.EntityManager;

@Repository
public class UserDAO implements IUserDAO {

    private EntityManager entityManager;
    private ProjectUsersDAO projectUsersDAO;

    private final String USER_NOT_FOUND_MESSAGE = "User not found for ID:";

    @Autowired
    public UserDAO(EntityManager entityManager, ProjectUsersDAO projectUsersDAO) {
        this.entityManager = entityManager;
        this.projectUsersDAO = projectUsersDAO;
    }

    @Override
    public void register(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE + userId);
        }
        return user;
    }

    @Override
    public void updateUser(long userId, User updatedUser) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE + userId);
        }

        String updatedEmail = updatedUser.getEmail();
        String updatedUsername = updatedUser.getUsername();
        if (updatedEmail != null) {
            user.setEmail(updatedEmail);
        }
        if (updatedUsername != null) {
            user.setUsername(updatedUsername);
        }
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE + userId);
        }
        entityManager.remove(user);
    }

    @Override
    public List<Project> getUsersProjects(long userId) {
        return projectUsersDAO.getUsersProjects(userId);
    }
}