package com.nathangilbert.projecttasking.orm.dao.interfaces;

import com.nathangilbert.projecttasking.orm.entity.Sprint;

public interface IProjectCurrentSprintDAO {
    Sprint getCurrentSprint(Long projectId);

    void setCurrentSprint(Long projectId, Sprint newSprint);
}
