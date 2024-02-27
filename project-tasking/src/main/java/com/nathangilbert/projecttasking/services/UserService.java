package com.nathangilbert.projecttasking.services;

import org.springframework.stereotype.Service;

import com.nathangilbert.projecttasking.orm.dao.UserDAO;
import com.nathangilbert.projecttasking.orm.entity.User;

@Service
public class UserService implements IUserService{
    
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void register(User user) {
        this.userDAO.register(user);
    }

    @Override
    public User findById(long userId) {
        return this.userDAO.findById(userId);
    }

    @Override
    public void updateUser(long userId, User user) {
        this.userDAO.updateUser(userId, user);
    }

    @Override
    public void deleteUser(long userId) {
        this.userDAO.deleteUser(userId);
    }
}
