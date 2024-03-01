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

import com.nathangilbert.projecttasking.orm.entity.Project;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.User;
import com.nathangilbert.projecttasking.services.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // User CRUD

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.OK).body("Successully registered user " + user.getUsername());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable long userId, @RequestBody User user) {
        userService.updateUser(userId, user);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<List<Project>> getProjects(@PathVariable long userId) {
        List<Project> projects = userService.getProjects(userId);
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getAllAssignedTasks(@PathVariable long userId) {
        List<Task> tasks = userService.getAllAssignedTasks(userId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/{userId}/tasks/{projectId}")
    public ResponseEntity<List<Task>> getAssignedTasksFromProject(@PathVariable long userId, @PathVariable long projectId) {
        List<Task> tasks = userService.getAllAssignedTasks(userId, projectId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

}
