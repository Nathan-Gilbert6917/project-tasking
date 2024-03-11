package com.nathangilbert.projecttasking.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.nathangilbert.projecttasking.orm.entity.Notification;
import com.nathangilbert.projecttasking.orm.entity.User;
import com.nathangilbert.projecttasking.services.NotificationService;


@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {
    
    private final NotificationService notificationService;

    @Autowired
    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @CrossOrigin
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeClient(@RequestBody User user) {
        notificationService.subscribeClient(user);
        return ResponseEntity.ok().body("Successfully subscribed user");
    }

    @CrossOrigin
    @GetMapping(value="/events/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getEvents(@PathVariable Long userId) {

        SseEmitter emitter = notificationService.getClientEvents(new User(userId));
        return emitter;
    }

    @PostMapping("/dispatch")
    public ResponseEntity<Notification> dispatchNotificationToClients(@RequestBody Notification notification) {
        Notification persistedNotification = notificationService.createNotification(notification);
        notificationService.dispatchNotificationToClients(persistedNotification.getRecipients(), persistedNotification);
        return ResponseEntity.ok().body(persistedNotification);
    }
}