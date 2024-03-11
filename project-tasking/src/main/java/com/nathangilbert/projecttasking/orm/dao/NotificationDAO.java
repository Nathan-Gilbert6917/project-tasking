package com.nathangilbert.projecttasking.orm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.orm.dao.interfaces.INotificationDAO;
import com.nathangilbert.projecttasking.orm.entity.Notification;

import jakarta.persistence.EntityManager;

@Repository
public class NotificationDAO implements INotificationDAO {

    private EntityManager entityManager;

    private final String NOTIFICATION_NOT_FOUND_MESSAGE = "Notification not found";

    @Autowired
    public NotificationDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Notification save(Notification notification) {
        // Add notification to the entity manager to be committed to the database
        entityManager.persist(notification);

        return notification;
    }

}
