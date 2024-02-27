package com.nathangilbert.projecttasking.orm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.rest.exceptions.UserNotFoundException;
import com.nathangilbert.projecttasking.orm.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class UserDAO implements IUserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void register(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new com.nathangilbert.projecttasking.rest.exceptions.UserNotFoundException("User not found for user " + userId);
        }
        return user;
    }

    @Override
    @Transactional
    public void updateUser(long userId, User updatedUser) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException("User not found for user " + userId);
        }

        String updatedEmail = updatedUser.getEmail();
        String updatedUsername = updatedUser.getUsername();
        if (updatedEmail != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUsername != null) {
            user.setUsername(updatedUser.getUsername());
        }
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException("User not found for user " + userId);
        }
        entityManager.remove(user);
    }
}