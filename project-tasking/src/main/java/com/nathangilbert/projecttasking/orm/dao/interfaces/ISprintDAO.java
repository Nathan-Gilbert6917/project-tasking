package com.nathangilbert.projecttasking.orm.dao.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Sprint;

public interface ISprintDAO {
    void createSprint(Sprint sprint);

    Sprint findById(Long sprintId);

    List<Sprint> getAllProjectSprints(Long projectId);

    void updateSprint(Long sprintId, Sprint updatedSprint);

    void deleteSprint(Long sprintId);
}
