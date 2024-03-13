package com.nathangilbert.projecttasking.services.interfaces;

import com.nathangilbert.projecttasking.orm.entity.Sprint;

public interface IProjectCurrentSprintService {

    Sprint getCurrentSprint(Long projectId);

    void setCurrentSprint(Long projectId, Sprint newSprint);

}
