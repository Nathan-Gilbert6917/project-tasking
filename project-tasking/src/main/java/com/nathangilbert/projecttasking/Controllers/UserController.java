package com.nathangilbert.projecttasking.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nathangilbert.projecttasking.exceptions.UserNotFoundException;
import com.nathangilbert.projecttasking.orm.dao.UserDAO;
import com.nathangilbert.projecttasking.orm.entity.User;

@RestController
public class UserController {

    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // User CRUD

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userDAO.register(user);
            return ResponseEntity.status(HttpStatus.OK).body("Successully registered user " + user.getUsername());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getUser(@PathVariable long userId) {
        try {
            User user = userDAO.findById(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found for user " + userId);
            }
            return ResponseEntity.status(HttpStatus.OK).body(user.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("An error occurred while processing your request. " + e.getMessage());
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable long userId, @RequestBody User user) {
        try {
            userDAO.updateUser(userId, user);
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("An error occurred while processing your request. " + e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        try {
            userDAO.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("An error occurred while processing your request. " + e.getMessage());
        }
    }
}
