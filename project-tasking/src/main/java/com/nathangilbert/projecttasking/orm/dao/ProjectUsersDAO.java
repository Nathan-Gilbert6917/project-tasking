package com.nathangilbert.projecttasking.orm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.orm.dao.interfaces.IProjectUsersDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.ProjectUsers;
import com.nathangilbert.projecttasking.rest.exceptions.ProjectNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@Repository
public class ProjectUsersDAO implements IProjectUsersDAO {

    private EntityManager entityManager;

    private final String PROJECT_USER_NOT_FOUND_MESSAGE = "Project user associations not found for ";
    private final String PROJECT_NOT_FOUND_MESSAGE = "Projects not found IDs:";

    @Autowired
    public ProjectUsersDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createProjectUserAssociation(ProjectUsers projectUser) {
        entityManager.persist(projectUser);
    }

    @Override
    public List<Long> getProjectUserIds(long projectId) {
        TypedQuery<ProjectUsers> projectUserQuery = entityManager.createQuery(
                "FROM ProjectUsers WHERE projectId=:projectId", ProjectUsers.class);
        projectUserQuery.setParameter("projectId", projectId);
        List<ProjectUsers> projectUserAssociations = projectUserQuery.getResultList();

        if (projectUserAssociations == null || projectUserAssociations.size() == 0) {
            throw new ProjectNotFoundException(PROJECT_USER_NOT_FOUND_MESSAGE + "ProjectId:" + projectId);
        }

        List<Long> userIds = new ArrayList<>();
        for (ProjectUsers projectUserAssociation : projectUserAssociations) { 
            userIds.add(projectUserAssociation.getUserId());
        }
        return userIds;
    }

    @Override
    public List<Project> getUsersProjects(long userId) {
        TypedQuery<ProjectUsers> projectUserQuery = entityManager.createQuery(
                "FROM ProjectUsers WHERE userId=:userId", ProjectUsers.class);
        projectUserQuery.setParameter("userId", userId);
        List<ProjectUsers> projectUserAssociations = projectUserQuery.getResultList();

        if (projectUserAssociations == null || projectUserAssociations.size() == 0) {
            throw new ProjectNotFoundException(PROJECT_USER_NOT_FOUND_MESSAGE + "UserId:" + userId);
        }   

        List<Long> projectIds = new ArrayList<>();
        for (ProjectUsers projectUserAssociation : projectUserAssociations) { 
            projectIds.add(projectUserAssociation.getProjectId());
        }

        TypedQuery<Project> projectQuery = entityManager.createQuery(
                "FROM Project WHERE projectId IN :projectIds", Project.class);
        projectQuery.setParameter("projectIds", projectIds);
        
        List<Project> projects = projectQuery.getResultList();
        if (projects == null || projects.size() == 0) {
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND_MESSAGE + projectIds);
        }
        return projects;
    }

    @Override
    public void deleteProjectUserAssociation(long projectId, long userId) {
        TypedQuery<ProjectUsers> projectUserQuery = entityManager.createQuery("FROM ProjectUsers WHERE projectId = :projectId AND userId = :userId",ProjectUsers.class);
        projectUserQuery.setParameter("projectId", projectId);
        projectUserQuery.setParameter("userId", userId);
        ProjectUsers projectUser;
        try {
            projectUser = projectUserQuery.getSingleResult();
        } catch (NoResultException exception) {
            throw new ProjectNotFoundException(
                    PROJECT_USER_NOT_FOUND_MESSAGE + "ProjectId:" + projectId + " UserId:" + userId);
        }
        entityManager.remove(projectUser);
    }
    
}