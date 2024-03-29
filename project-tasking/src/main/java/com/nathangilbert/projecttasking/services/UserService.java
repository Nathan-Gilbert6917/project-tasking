package com.nathangilbert.projecttasking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nathangilbert.projecttasking.orm.dao.UserDAO;
import com.nathangilbert.projecttasking.orm.dao.UserTaskDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.User;
import com.nathangilbert.projecttasking.services.interfaces.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService{
    
    private UserDAO userDAO;
    private UserTaskDAO userTaskDAO;

    public UserService(UserDAO userDAO, UserTaskDAO userTaskDAO) {
        this.userDAO = userDAO;
        this.userTaskDAO = userTaskDAO;
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

    @Override
    public List<Task> getAllAssignedTasks(long userId) {
        return this.userTaskDAO.getAllAssignedTasks(userId);
    }

    @Override
    public List<Task> getAllAssignedTasks(long userId, long projectId) {
        return this.userTaskDAO.getAllAssignedTasks(userId, projectId);
    }
}
