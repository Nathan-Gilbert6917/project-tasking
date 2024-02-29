package com.nathangilbert.projecttasking.services;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Project;

public interface IProjectService {

    void createProject(Project user);

    Project findById(long projectId);

    List<Project> getOwnersProjects(long ownerId);

    List<Long> getProjectUserIds(Long projectId);

    void updateProject(long userId, Project user);

    void deleteProject(long userId);

    void joinProject(long projectId, long userId);

    void leaveProject(long projectId, long userId);
}