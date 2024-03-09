package com.nathangilbert.projecttasking.rest.controllers;

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

import com.nathangilbert.projecttasking.orm.entity.TaskComment;
import com.nathangilbert.projecttasking.services.TaskCommentService;

@RestController
@RequestMapping(value = "/api/tasks/comments")
public class TaskCommentRestController {

    private TaskCommentService taskCommentService;

    @Autowired
    public TaskCommentRestController(TaskCommentService taskCommentService) {
        this.taskCommentService = taskCommentService;
    }

    // Task Comment CRUD

    @PostMapping("/create")
    public ResponseEntity<String> createCommentOnTask(@RequestBody TaskComment taskComment) {
        taskCommentService.createTaskComment(taskComment);
        return ResponseEntity.status(HttpStatus.OK).body("Successully created comment on task:" + taskComment.getTaskId());
    }

    @GetMapping("/{taskCommentId}")
    public ResponseEntity<TaskComment> getTaskComment(@PathVariable long taskCommentId) {
        TaskComment taskComment = taskCommentService.findById(taskCommentId);
        return ResponseEntity.status(HttpStatus.OK).body(taskComment);
    }

    @PutMapping("/{taskCommentId}")
    public ResponseEntity<String> updateTaskComment(@PathVariable long taskCommentId, @RequestBody TaskComment taskComment) {
        taskCommentService.updateTaskComment(taskCommentId, taskComment);
        return ResponseEntity.status(HttpStatus.OK).body("Task comment updated successfully");
    }

    @DeleteMapping("/{taskCommentId}")
    public ResponseEntity<String> deleteTaskComment(@PathVariable long taskCommentId) {
        taskCommentService.deleteTaskComment(taskCommentId);
        return ResponseEntity.status(HttpStatus.OK).body("Task comment deleted successfully");
    }

}
