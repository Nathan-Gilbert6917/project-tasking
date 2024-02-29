package com.nathangilbert.projecttasking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nathangilbert.projecttasking.orm.dao.UserDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService{
    
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void register(User user) {
        this.userDAO.register(user);
    }

    @Override
    public User findById(long userId) {
        return this.userDAO.findById(userId);
    }

    @Override
    @Transactional
    public void updateUser(long userId, User user) {
        this.userDAO.updateUser(userId, user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        this.userDAO.deleteUser(userId);
    }

    @Override
    public List<Project> getProjects(long userId) {
        return this.userDAO.getUsersProjects(userId);
    }
}
