package com.mongodb.securerag;

public class ChatRequest {
    private String message;
    private String userRole;
    private String department;

    public ChatRequest() {}

    public ChatRequest(String message, String userRole, String department) {
        this.message = message;
        this.userRole = userRole;
        this.department = department;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}