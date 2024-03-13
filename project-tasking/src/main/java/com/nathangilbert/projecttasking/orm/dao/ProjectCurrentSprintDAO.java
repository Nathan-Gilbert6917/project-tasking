package com.nathangilbert.projecttasking.orm.dao;

import com.nathangilbert.projecttasking.orm.dao.interfaces.IProjectCurrentSprintDAO;
import com.nathangilbert.projecttasking.orm.entity.ProjectCurrentSprint;
import com.nathangilbert.projecttasking.orm.entity.Sprint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProjectCurrentSprintDAO implements IProjectCurrentSprintDAO {

    private final EntityManager entityManager;

    public ProjectCurrentSprintDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Sprint getCurrentSprint(Long projectId) {
        TypedQuery<Sprint> currentSprintQuery = entityManager.createQuery(
            "SELECT sprintId DROM Project WHERE projectId=:projectId", Sprint.class
        );
        currentSprintQuery.setParameter("projectId", projectId);
        return currentSprintQuery.getSingleResult();
    }

    @Override
    public void setCurrentSprint(Long projectId, Sprint newSprint) {
        ProjectCurrentSprint currentSprintAssociation = this.entityManager.find(ProjectCurrentSprint.class, projectId);
        currentSprintAssociation.setSprint(newSprint);
    }
}
