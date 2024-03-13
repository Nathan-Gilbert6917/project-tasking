package com.nathangilbert.projecttasking.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.nathangilbert.projecttasking.orm.dao.NotificationDAO;
import com.nathangilbert.projecttasking.orm.entity.Notification;
import com.nathangilbert.projecttasking.orm.entity.User;
import com.nathangilbert.projecttasking.services.interfaces.INotificationService;

import jakarta.transaction.Transactional;

@Service
public class NotificationService implements INotificationService {

    private final Map<Long, SseEmitter> clientEmitterMap = new ConcurrentHashMap<>();

    private NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    @Transactional
    public Notification createNotification(Notification notification) {
        // Add the notification to the Database
        Notification persistedNotification = notificationDAO.save(notification);
        return persistedNotification;
    }

    @Override
    public void subscribeClient(User user) {
        Long userId = user.getUserId();
        SseEmitter emitter = new SseEmitter(60_000L);
        emitter.onTimeout(emitter::complete);
        clientEmitterMap.put(userId, emitter);
    }

    @Override
    public SseEmitter getClientEvents(User user) {
        Long userId = user.getUserId();
        return clientEmitterMap.get(userId);
    }

    @Override
    public void dispatchNotificationToClients(List<User> clients, Notification notification) {
        for (User client : clients) {
            SseEmitter emitter = clientEmitterMap.get(client.getUserId());
            if (emitter == null) {
                continue;
            }
            String eventId = String.valueOf(notification.getNotificationsId());
            if (eventId == null) {
                Random random = new Random();
                int randomIndex = random.nextInt();
                eventId = "id-"+ randomIndex;
            }
            String eventName = notification.getTypeString();
            SseEventBuilder event = SseEmitter.event().id(eventId).name(eventName).data(notification);
            try {
                emitter.send(event);
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }
    }

}
