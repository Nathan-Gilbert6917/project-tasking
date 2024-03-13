package com.nathangilbert.projecttasking.services;

import com.nathangilbert.projecttasking.orm.dao.ProjectCurrentSprintDAO;
import com.nathangilbert.projecttasking.orm.entity.Sprint;
import com.nathangilbert.projecttasking.services.interfaces.IProjectCurrentSprintService;

import jakarta.transaction.Transactional;

public class ProjectCurrentSprintService implements IProjectCurrentSprintService{

    private final ProjectCurrentSprintDAO projectCurrentSprintDAO;

    public ProjectCurrentSprintService(ProjectCurrentSprintDAO projectCurrentSprintDAO) {
        this.projectCurrentSprintDAO = projectCurrentSprintDAO;
    }

    @Override
    public Sprint getCurrentSprint(Long projectId) {
        return this.projectCurrentSprintDAO.getCurrentSprint(projectId);
    }

    @Override
    @Transactional
    public void setCurrentSprint(Long projectId, Sprint newSprint) {
        this.projectCurrentSprintDAO.setCurrentSprint(projectId, newSprint);
    }

}
