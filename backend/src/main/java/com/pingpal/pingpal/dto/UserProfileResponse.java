package com.pingpal.pingpal.dto;

public class UserProfileResponse {
    private Long id;
    private String fullName;
    private String name;
    private String email;

    public UserProfileResponse(Long id, String fullName, String name, String email) {
        this.id = id;
        this.fullName = fullName;
        this.name = name;
        this.email = email;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
