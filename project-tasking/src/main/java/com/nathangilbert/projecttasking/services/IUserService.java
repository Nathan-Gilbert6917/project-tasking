package com.nathangilbert.projecttasking.services;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.User;

public interface IUserService {

    void register(User user);

    User findById(long userId);

    void updateUser(long userId, User user);

    void deleteUser(long userId);

    List<Project> getProjects(long userId);
}
