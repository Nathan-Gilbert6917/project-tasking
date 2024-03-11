package com.nathangilbert.projecttasking.orm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.sql.Timestamp;
import java.util.List;

import com.nathangilbert.projecttasking.orm.enums.NotificationEventType;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationsId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationEventType type;

    @Column(name = "message")
    private String message;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToMany
    @JoinTable(
            name = "notification_recipients",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    private List<User> recipients;

    @Column(name = "created_at")
    private Timestamp createdAt;


    public Notification() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Notification(NotificationEventType type, String message, String content) {
        this.type = type;
        this.content = content;
        this.message = message;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Notification(NotificationEventType type, String message, String content, User sender, List<User> recipients) {
        this.type = type;
        this.content = content;
        this.message = message;
        this.sender = sender;
        this.recipients = recipients;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public long getNotificationsId() {
        return this.notificationsId;
    }

    public void setNotificationsId(Long notificationsId) {
        this.notificationsId = notificationsId;
    }

    public NotificationEventType getType() {
        return type;
    }

    public void setType(NotificationEventType type) {
        this.type = type;
    }

    public String getTypeString() {
        return type.getEventName();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User user) {
        this.sender = user;
    }

    public List<User> getRecipients() {
        return this.recipients;
    }

    public void setRecipients(List<User> users) {
        this.recipients = users;
    }

    public void addRecipient(User user) {
        if (user != null)
            this.recipients.add(user);
    }
}
