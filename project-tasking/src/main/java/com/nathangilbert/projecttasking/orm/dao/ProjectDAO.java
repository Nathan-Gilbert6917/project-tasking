package com.nathangilbert.projecttasking.orm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.rest.exceptions.ProjectNotFoundException;
import com.nathangilbert.projecttasking.orm.dao.interfaces.IProjectDAO;
import com.nathangilbert.projecttasking.orm.entity.Project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class ProjectDAO implements IProjectDAO {

    private EntityManager entityManager;

    private final String PROJECT_NOT_FOUND_MESSAGE = "Project not found for ID:";

    private final String PROJECTS_NOT_FOUND_MESSAGE = "No projects owned by UserID:";

    @Autowired
    public ProjectDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createProject(Project project) {
        entityManager.persist(project);
    }

    @Override
    public Project findById(long projectId) {
        Project project = entityManager.find(Project.class, projectId);
        if (project == null) {
            throw new ProjectNotFoundException(
                    PROJECT_NOT_FOUND_MESSAGE + projectId);
        }
        return project;
    }

    @Override
    public void updateProject(long projectId, Project updatedProject) {
        Project project = entityManager.find(Project.class, projectId);
        if (project == null) {
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND_MESSAGE + projectId);
        }

        String updatedProjectName = updatedProject.getProjectName();
        String updatedProjectDescription = updatedProject.getDescription();
        int updatedProjectAgingTaskDays = updatedProject.getAgingTaskDays();
    
        if (updatedProjectName != null) {
            project.setProjectName(updatedProjectName);
        }

        if (updatedProjectDescription != null) {
            project.setDescription(updatedProjectDescription);
        }

        if (updatedProjectAgingTaskDays >= 0 && updatedProjectAgingTaskDays != project.getAgingTaskDays()) {
            project.setAgingTaskDays(updatedProjectAgingTaskDays);
        }

        entityManager.merge(project);
    }

    @Override
    public void deleteProject(long projectId) {
        Project project = entityManager.find(Project.class, projectId);
        if (project == null) {
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND_MESSAGE + projectId);
        }
        entityManager.remove(project);
    }

    @Override
    public List<Project> getOwnersProjects(long ownerId) {
        TypedQuery<Project> projectQuery = entityManager.createQuery(
                "FROM Project WHERE ownerId=:ownerId", Project.class);
        projectQuery.setParameter("ownerId", ownerId);
        List<Project> userOwnedProjects = projectQuery.getResultList();

        if (userOwnedProjects == null || userOwnedProjects.size() == 0) {
            throw new ProjectNotFoundException(PROJECTS_NOT_FOUND_MESSAGE + ownerId);
        }

        return userOwnedProjects;
    }
}