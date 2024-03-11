package com.nathangilbert.projecttasking.services.interfaces;

import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.nathangilbert.projecttasking.orm.entity.Notification;
import com.nathangilbert.projecttasking.orm.entity.User;

public interface INotificationService {

    Notification createNotification(Notification notification);

    void subscribeClient(User user);

    SseEmitter getClientEvents(User user);

    void dispatchNotificationToClients(List<User> clients, Notification notification);
}
