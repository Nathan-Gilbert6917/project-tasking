package com.nathangilbert.projecttasking.services.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.Sprint;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.User;

public interface IProjectService {

    void createProject(Project user);

    Project findById(long projectId);

    List<Project> getOwnersProjects(long ownerId);

    List<Long> getProjectUserIds(Long projectId);

    void updateProject(long userId, Project user);

    void deleteProject(long userId);

    void joinProject(long projectId, long userId);

    void leaveProject(long projectId, long userId);

    List<Task> getProjectTasks(long projectId);

    Sprint getCurrentSprint(Long projectId);

    void setCurrentSprint(long projectId, Sprint newSprint);

    void inviteUsersToProject(Project project, List<Long> recipientIds, User sender);
}