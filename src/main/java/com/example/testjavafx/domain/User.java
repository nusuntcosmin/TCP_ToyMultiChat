package com.example.testjavafx.domain;

import java.util.UUID;

public class User extends Entity<String> {

    private String userEmail;
    private String username;

    public String getUserPassword() {
        return userPassword;
    }

    private String userPassword;

    public User(String userEmail, String username,String userPassword) {
        this.userEmail = userEmail;
        super.setEntityID(userEmail);
        this.username = username;
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsername() {
        return username;
    }
}
