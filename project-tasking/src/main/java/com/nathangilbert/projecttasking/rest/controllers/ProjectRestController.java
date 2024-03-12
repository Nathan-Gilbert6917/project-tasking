package com.nathangilbert.projecttasking.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nathangilbert.projecttasking.orm.entity.InviteRequest;
import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.services.ProjectService;

@RestController
@RequestMapping(value = "/api/projects")
public class ProjectRestController {

    private ProjectService projectService;

    @Autowired
    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // User CRUD

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody Project project) {
        projectService.createProject(project);
        projectService.joinProject(project.getProjectId(), project.getOwnerId());
        return ResponseEntity.status(HttpStatus.OK).body("Successully created project:" + project);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable long projectId) {
        Project project = projectService.findById(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<String> updateProject(@PathVariable long projectId, @RequestBody Project project) {
        projectService.updateProject(projectId, project);
        return ResponseEntity.status(HttpStatus.OK).body("Project updated successfully");
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully");
    }

    // Adding a new user to the project

    @PostMapping("/{projectId}/add/{userId}")
    public ResponseEntity<String> addUser(@PathVariable long projectId, @PathVariable long userId) {
        projectService.joinProject(projectId, userId);
        return ResponseEntity.status(HttpStatus.OK).body("Successully added User:" + userId + " to Project:" + projectId);
    }

    // Removing a user from the project

    @PostMapping("/{projectId}/remove/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable long projectId, @PathVariable long userId) {
        projectService.leaveProject(projectId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successully removed User:" + userId + " from Project:" + projectId);
    }

    // Get all projects owned by a user

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Project>> getOwnersProjects(@PathVariable long ownerId) {
        List<Project> projects = projectService.getOwnersProjects(ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    // Get all tasks associated with a project

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<Task>> getAllProjectTasks(@PathVariable long projectId) {
        List<Task> tasks = projectService.getProjectTasks(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    // Get all user ids associated with a project

    @GetMapping("/{projectId}/users")
    public ResponseEntity<List<Long>> getProjectUserIds(@PathVariable long projectId) {
        List<Long> userIds = projectService.getProjectUserIds(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(userIds);
    }

    @PostMapping("/{projectId}/invite")
    public ResponseEntity<String> inviteUsersToProject(@PathVariable long projectId, @RequestBody InviteRequest request) {
        Project project = projectService.findById(projectId);
        String projectName = project.getProjectName();

        projectService.inviteUsersToProject(project, request.getRecipienttIds(), request.getSender());

        return ResponseEntity.status(HttpStatus.OK).body("Successfully invited users to the project " + projectName);
    }
    
}
