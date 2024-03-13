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

import com.nathangilbert.projecttasking.orm.entity.Sprint;
import com.nathangilbert.projecttasking.services.SprintService;

@RestController
@RequestMapping(value = "/api/sprints")
public class SprintRestController {

    private final SprintService sprintService;

    @Autowired
    public SprintRestController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    // Sprint CRUD

    @PostMapping("/create")
    public ResponseEntity<String> createSprint(@RequestBody Sprint sprint) {
        this.sprintService.createSprint(sprint);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully created sprint");
    }

    @GetMapping("/{sprintId}")
    public ResponseEntity<Sprint> getSprint(@PathVariable Long sprintId) {
        Sprint sprint = this.sprintService.findById(sprintId);
        return ResponseEntity.status(HttpStatus.OK).body(sprint);
    }

    @PutMapping("/{sprintId}")
    public ResponseEntity<String> updateSprint(@PathVariable Long sprintId, @RequestBody Sprint sprint) {
        this.sprintService.updateSprint(sprintId, sprint);
        return ResponseEntity.status(HttpStatus.OK).body("Sprint updated successfully");
    }

    @DeleteMapping("/{sprintId}")
    public ResponseEntity<String> deleteSprint(@PathVariable Long sprintId) {
        this.sprintService.deleteSprint(sprintId);
        return ResponseEntity.status(HttpStatus.OK).body("Sprint deleted successfully");
    }
}
