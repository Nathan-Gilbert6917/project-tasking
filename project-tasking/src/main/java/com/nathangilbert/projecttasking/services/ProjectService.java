package com.nathangilbert.projecttasking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nathangilbert.projecttasking.orm.dao.ProjectDAO;
import com.nathangilbert.projecttasking.orm.dao.ProjectUsersDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.ProjectUsers;

import jakarta.transaction.Transactional;

@Service
public class ProjectService implements IProjectService{
    
    private ProjectDAO projectDAO;
    private ProjectUsersDAO projectUsersDAO;

    public ProjectService(ProjectDAO projectDAO, ProjectUsersDAO projectUsersDAO) {
        this.projectDAO = projectDAO;
        this.projectUsersDAO = projectUsersDAO;
    }

    @Override
    @Transactional
    public void createProject(Project project) {
        this.projectDAO.createProject(project);
    }

    @Override
    public Project findById(long projectId) {
        return this.projectDAO.findById(projectId);
    }

    @Override
    @Transactional
    public void updateProject(long projectId, Project project) {
        this.projectDAO.updateProject(projectId, project);
    }

    @Override
    @Transactional
    public void deleteProject(long projectId) {
        this.projectDAO.deleteProject(projectId);
    }

    @Override
    public List<Project> getOwnersProjects(long ownerId) {
        return this.projectDAO.getOwnersProjects(ownerId);
    }

    @Override
    public List<Long> getProjectUserIds(Long projectId) {
        return this.projectUsersDAO.getProjectUserIds(projectId);
    }

    @Override
    @Transactional
    public void joinProject(long projectId, long userId) {
        this.projectUsersDAO.createProjectUserAssociation(new ProjectUsers(userId, projectId));
    }

    @Override
    @Transactional
    public void leaveProject(long projectId, long userId) {
        this.projectUsersDAO.deleteProjectUserAssociation(projectId, userId);
    }
}
