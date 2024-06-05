package com.example.phonebookui.responses;

public class ContactResponse {
    private String phoneNumber;
    private String name;
    private String userId;
    private boolean selected;
    private String id;

    public ContactResponse(String phoneNumber, String name, String userId, boolean selected, String id) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.userId = userId;
        this.selected = selected;
        this.id = id;
    }

    public ContactResponse(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ContactResponse() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" + "\"phoneNumber\":\"" + phoneNumber + '\"' +
                ", \"name\":\"" + name + '\"' + '}';
    }
}
