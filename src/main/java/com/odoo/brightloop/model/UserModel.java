package com.odoo.brightloop.model;

public class UserModel {
    private int userId;
    private String emailId;
    private String password;
    private String role;

    //Constructor
    public UserModel(){
        
    }

    public UserModel(
        int userId, 
        String emailId, 
        String password, 
        String role
    ) {
        this.userId = userId;
        this.emailId = emailId;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}