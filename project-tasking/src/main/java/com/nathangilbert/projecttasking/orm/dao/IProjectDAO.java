package com.nathangilbert.projecttasking.orm.dao;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Project;

public interface IProjectDAO {

    void createProject(Project project);

    Project findById(long projectId);

    List<Project> getOwnersProjects(long projectId);

    void updateProject(long projectId, Project updatedProject);

    void deleteProject(long projectId);
}