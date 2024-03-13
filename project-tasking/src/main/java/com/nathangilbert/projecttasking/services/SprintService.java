package com.nathangilbert.projecttasking.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;

import com.nathangilbert.projecttasking.orm.dao.SprintDAO;
import com.nathangilbert.projecttasking.orm.entity.Sprint;
import com.nathangilbert.projecttasking.services.interfaces.ISprintService;

import jakarta.transaction.Transactional;


public class SprintService implements ISprintService {

    private final SprintDAO sprintDAO;

    public SprintService(SprintDAO sprintDAO) {
        this.sprintDAO = sprintDAO;
    }

    @Override
    @Transactional
    public void createSprint(Sprint sprint) {
        this.sprintDAO.createSprint(sprint);    
    }

    @Override
    public Sprint findById(Long sprintId) {
        return this.sprintDAO.findById(sprintId);
    }

    @Override
    public List<Sprint> getAllProjectSprints(Long projectId) {
        return this.sprintDAO.getAllProjectSprints(projectId);
    }

    @Override
    @Transactional
    public void updateSprint(Long sprintId, Sprint updatedSprint) {
        this.sprintDAO.updateSprint(sprintId, updatedSprint);
    }

    @Override
    @Transactional
    public void deleteSprint(Long sprintId) {
        this.sprintDAO.deleteSprint(sprintId);
    }
    
}
