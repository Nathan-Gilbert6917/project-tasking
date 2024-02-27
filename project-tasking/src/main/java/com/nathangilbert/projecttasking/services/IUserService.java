package com.nathangilbert.projecttasking.services;

import com.nathangilbert.projecttasking.orm.entity.User;

public interface IUserService {

    void register(User user);

    User findById(long userId);

    void updateUser(long userId, User user);

    void deleteUser(long userId);
}
