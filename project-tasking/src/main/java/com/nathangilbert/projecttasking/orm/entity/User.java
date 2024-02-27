package com.nathangilbert.projecttasking.orm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = false, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and setters

    public long getUserId() {
        return this.userId;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAtTime() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "User {" +
            "userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            "}";
    }

}