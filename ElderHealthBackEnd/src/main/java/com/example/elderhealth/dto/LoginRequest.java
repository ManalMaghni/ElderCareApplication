package com.example.elderhealth.dto;

public class LoginRequest {
    private String login;
    private String password;

    // Default constructor
    public LoginRequest() {}

    // Getters and Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

