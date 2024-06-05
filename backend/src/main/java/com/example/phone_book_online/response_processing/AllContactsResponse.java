package com.example.phone_book_online.response_processing;

import com.example.phone_book_online.models.contact.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllContactsResponse {
    private ArrayList<ContactResponse> allContacts;

    public AllContactsResponse(List<ContactResponse> allContacts) {
        this.allContacts = new ArrayList<>(allContacts);
    }
}
