package com.nathangilbert.projecttasking.orm.entity;

import java.util.List;

public class InviteRequest {
    private User sender;
    private List<Long> recipientIds;

    public InviteRequest() {}

    public InviteRequest(User sender, List<Long> recipientIds) {
        this.sender = sender;
        this.recipientIds = recipientIds;
    }

    // Getters and setters

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<Long> getRecipienttIds() {
        return recipientIds;
    }

    public void setRecipientIds(List<Long> recipientIds) {
        this.recipientIds = recipientIds;
    }

    public void addRecipientId(Long recipientId) {
        if (recipientId != null) {
            this.recipientIds.add(recipientId);
        }
    }
}