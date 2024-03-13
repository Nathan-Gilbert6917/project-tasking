package com.nathangilbert.projecttasking.orm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nathangilbert.projecttasking.orm.dao.interfaces.ISprintDAO;
import com.nathangilbert.projecttasking.orm.entity.Sprint;
import com.nathangilbert.projecttasking.rest.exceptions.SprintNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class SprintDAO implements ISprintDAO {
private EntityManager entityManager;

    private final String SPRINT_NOT_FOUND_MESSAGE = "Sprint not found for ID:";

    @Autowired
    public SprintDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createSprint(Sprint sprint) {
        entityManager.persist(sprint);
    }

    @Override
    public Sprint findById(Long sprintId) {
        Sprint sprint = entityManager.find(Sprint.class, sprintId);
    
        if (sprint == null) {
            throw new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE);
        }
        return sprint;
    }

    @Override
    public List<Sprint> getAllProjectSprints(Long projectId) {
        String jpql = "FROM Sprints WHERE projectId=:projectId ORDER BY created_at DESC";
        TypedQuery<Sprint> projectSprintsQuery = entityManager.createQuery(jpql, Sprint.class);
        projectSprintsQuery.setParameter("projectId",projectId);
        List<Sprint> sprints = projectSprintsQuery.getResultList();
        if (sprints == null || sprints.size() == 0) {
            throw new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE + projectId);
        }
        return sprints;
    }

    @Override
    public void updateSprint(Long sprintId, Sprint updatedSprint) {
        Sprint sprint = entityManager.find(Sprint.class, sprintId);
        if (sprint == null) {
            throw new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE + sprintId);
        }

        String updatedSprintName = updatedSprint.getSprintName();
        String updatedDescription = updatedSprint.getDescription();
        String updatedRetrospectiveNotes = updatedSprint.getRetrospectiveNotes();

        if (updatedSprintName != null) {
            sprint.setSprintName(updatedSprintName);
        }
        if (updatedDescription != null) {
            sprint.setDescription(updatedDescription);
        }
        if (updatedRetrospectiveNotes != null) {
            sprint.setRetrospectiveNotes(updatedRetrospectiveNotes);
        }

        entityManager.merge(sprint);
    }

    @Override
    public void deleteSprint(Long sprintId) {
        Sprint sprint = entityManager.find(Sprint.class, sprintId);
        if (sprint == null) {
            throw new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE + sprintId);
        }
        entityManager.remove(sprint);
    }

}
