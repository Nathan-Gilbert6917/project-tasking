package com.nathangilbert.projecttasking.orm.dao;

import com.nathangilbert.projecttasking.orm.entity.User;

public interface IUserDAO {

    void register(User user);

    User findById(long userId);

    void updateUser(long userId, User user);

    void deleteUser(long userId);
}