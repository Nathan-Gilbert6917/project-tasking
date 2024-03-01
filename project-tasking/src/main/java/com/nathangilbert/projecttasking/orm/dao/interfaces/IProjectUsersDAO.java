package com.nathangilbert.projecttasking.orm.dao.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.ProjectUsers;

public interface IProjectUsersDAO {

    void createProjectUserAssociation(ProjectUsers projectUser);

    List<Long> getProjectUserIds(long projectId);

    List<Project> getUsersProjects(long userId);

    void deleteProjectUserAssociation(long projectId, long userId);
    
}