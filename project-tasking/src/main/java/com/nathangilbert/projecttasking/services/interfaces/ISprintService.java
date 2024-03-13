package com.nathangilbert.projecttasking.services.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Sprint;

public interface ISprintService {
    void createSprint(Sprint sprint);
    
    Sprint findById(Long sprintId);

    List<Sprint> getAllProjectSprints(Long projectId);

    void updateSprint(Long sprintId, Sprint sprint);

    void deleteSprint(Long sprintId);

}
