package com.nathangilbert.projecttasking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nathangilbert.projecttasking.orm.dao.ProjectDAO;
import com.nathangilbert.projecttasking.orm.dao.ProjectUsersDAO;
import com.nathangilbert.projecttasking.orm.dao.TaskDAO;
import com.nathangilbert.projecttasking.orm.entity.Notification;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.ProjectUsers;
import com.nathangilbert.projecttasking.orm.entity.Sprint;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.User;
import com.nathangilbert.projecttasking.orm.enums.NotificationEventType;
import com.nathangilbert.projecttasking.services.interfaces.IProjectService;

import jakarta.transaction.Transactional;

@Service
public class ProjectService implements IProjectService{
    
    private ProjectDAO projectDAO;
    private ProjectUsersDAO projectUsersDAO;
    private TaskDAO taskDAO;

    private NotificationService notificationService;
    private ProjectCurrentSprintService projectCurrentSprintService;

    public ProjectService(ProjectDAO projectDAO, ProjectUsersDAO projectUsersDAO, TaskDAO taskDAO, NotificationService notificationService, ProjectCurrentSprintService projectCurrentSprintService) {
        this.projectDAO = projectDAO;
        this.projectUsersDAO = projectUsersDAO;
        this.taskDAO = taskDAO;
        this.notificationService = notificationService;
        this.projectCurrentSprintService = projectCurrentSprintService;
    }

    @Override
    @Transactional
    public void createProject(Project project) {
        this.projectDAO.createProject(project);
    }

    @Override
    public Project findById(long projectId) {
        return this.projectDAO.findById(projectId);
    }

    @Override
    @Transactional
    public void updateProject(long projectId, Project project) {
        this.projectDAO.updateProject(projectId, project);
    }

    @Override
    @Transactional
    public void deleteProject(long projectId) {
        this.projectDAO.deleteProject(projectId);
    }

    @Override
    public List<Project> getOwnersProjects(long ownerId) {
        return this.projectDAO.getOwnersProjects(ownerId);
    }

    @Override
    public List<Long> getProjectUserIds(Long projectId) {
        return this.projectUsersDAO.getProjectUserIds(projectId);
    }

    @Override
    @Transactional
    public void joinProject(long projectId, long userId) {
        ProjectUsers projectUsers = new ProjectUsers(userId, projectId);
        this.projectUsersDAO.createProjectUserAssociation(projectUsers);
    }

    @Override
    @Transactional
    public void leaveProject(long projectId, long userId) {
        this.projectUsersDAO.deleteProjectUserAssociation(projectId, userId);
    }

    @Override
    public List<Task> getProjectTasks(long projectId) {
        return this.taskDAO.getProjectTasks(projectId);
    }

    @Override
    public Sprint getCurrentSprint(Long projectId) {
        return projectCurrentSprintService.getCurrentSprint(projectId);
    }

    @Override
    public void setCurrentSprint(long projectId, Sprint newSprint) {
        this.projectCurrentSprintService.setCurrentSprint(projectId, newSprint);
    }

    @Override
    @Transactional
    public void inviteUsersToProject(Project project, List<Long> recipientIds, User sender) {
        long projectId = project.getProjectId();
        String projectName = project.getProjectName();
        Notification newNotification = new Notification();

        StringBuilder jsonContent = new StringBuilder();

        jsonContent.append("{");
        jsonContent.append("'projectId:'"+projectId+",");
        jsonContent.append("'projectName:'"+projectName);
        jsonContent.append("}");

        newNotification.setContent(jsonContent.toString());
        newNotification.setMessage("You've been invited by "+sender.getUsername()+" to join Project: "+projectName);
        newNotification.setSender(sender);
        newNotification.setType(NotificationEventType.PROJECT_INVITE);

        List<User> recipients = new ArrayList<>();
        for (long recipientId : recipientIds) {
            recipients.add(new User(recipientId));
        }

        newNotification.setRecipients(recipients);
        Notification notification = this.notificationService.createNotification(newNotification);

        this.notificationService.dispatchNotificationToClients(notification.getRecipients(), notification);
    }
}
