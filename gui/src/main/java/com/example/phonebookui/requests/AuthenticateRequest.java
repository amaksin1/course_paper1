package com.example.phonebookui.requests;

public class AuthenticateRequest {
    private String email;
    private String password;

    public AuthenticateRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticateRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"email\": \""+this.email+"\", \"password\": \"" + this.password + "\"}";
    }
}
