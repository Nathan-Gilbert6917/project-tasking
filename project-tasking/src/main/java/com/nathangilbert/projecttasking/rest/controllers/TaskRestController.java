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
import org.springframework.web.bind.annotation.RestController;

import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.TaskComment;
import com.nathangilbert.projecttasking.orm.entity.TaskStatusHistory;
import com.nathangilbert.projecttasking.services.TaskCommentService;
import com.nathangilbert.projecttasking.services.TaskService;
import com.nathangilbert.projecttasking.services.TaskStatusHistoryService;


@RestController
@RequestMapping(value = "/api/tasks")
public class TaskRestController {

    private final TaskService taskService;
    private final TaskCommentService taskCommentService;
    private final TaskStatusHistoryService taskStatusHistoryService;

    @Autowired
    public TaskRestController(TaskService taskService, TaskCommentService taskCommentService, TaskStatusHistoryService taskStatusHistoryService) {
        this.taskService = taskService;
        this.taskCommentService = taskCommentService;
        this.taskStatusHistoryService = taskStatusHistoryService;
    }

    // Task CRUD

    @PostMapping("/create")
    public ResponseEntity<String> createProjectTask(@RequestBody Task task) {
        taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.OK).body("Successully created task:" + task);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable long taskId) {
        Task task = taskService.findById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable long taskId, @RequestBody Task task) {
        taskService.updateTask(taskId, task);
        // Dispatch Notification
        
        return ResponseEntity.status(HttpStatus.OK).body("Task updated successfully");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }

    // Assign a user to a task

    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<String> assignUserToTask(@PathVariable long taskId, @PathVariable long userId) {
        taskService.assignTask(userId, taskId);
        // Dispatch Notification
        

        return ResponseEntity.status(HttpStatus.OK)
                .body("Successully assigned User:" + userId + " to Task:" + taskId);
    }

    // Unassign a user from a task

    @PostMapping("/{taskId}/unassign/{userId}")
    public ResponseEntity<String> unassignUserFromTask(@PathVariable long taskId, @PathVariable long userId) {
        taskService.unassignTask(userId, taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successully unassign User:" + userId + " from Task:" + taskId);
    }

    // Gather a list of comments for a task

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<TaskComment>> getCommentOnTask(@PathVariable long taskId) {
        List<TaskComment> taskComment = taskCommentService.getCommentsOnTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(taskComment);
    }

    // Gather the most recent task status update

    @GetMapping("/{taskId}/statuses/history")
    public ResponseEntity<TaskStatusHistory> getRecentTasksStatusHistory(@PathVariable Long taskId) {
        TaskStatusHistory statusHistory = taskStatusHistoryService.getLatestTaskStatusHistory(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(statusHistory);
    }

    // Gather a list of all task status updates recent to past

    @GetMapping("/{taskId}/statuses/history")
    public ResponseEntity<List<TaskStatusHistory>> getTasksStatusHistory(@PathVariable Long taskId) {
        List<TaskStatusHistory> statusHistories = taskStatusHistoryService.getAllTaskStatusHistory(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(statusHistories);
    }

}
