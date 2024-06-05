package com.example.phonebookui.responses;

import java.util.ArrayList;
import java.util.List;

public class AllContactsResponse {
    private List<ContactResponse> allContacts;

    public AllContactsResponse(List<ContactResponse> allContacts) {
        this.allContacts = new ArrayList<>(allContacts);
    }

    public AllContactsResponse() {
    }

    public List<ContactResponse> getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(List<ContactResponse> allContacts) {
        this.allContacts = allContacts;
    }
}
