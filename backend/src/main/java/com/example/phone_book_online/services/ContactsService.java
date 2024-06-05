package com.example.phone_book_online.services;

import com.example.phone_book_online.models.contact.Contact;
import com.example.phone_book_online.models.contact.ContactRepository;
import com.example.phone_book_online.models.user.UserRepository;
import com.example.phone_book_online.request_processing.ContactRequest;
import com.example.phone_book_online.response_processing.AllContactsResponse;
import com.example.phone_book_online.response_processing.ContactResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactsService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ContactResponse register(ContactRequest contactRequest, String jwt) {
        var contact = Contact.builder()
                .phoneNumber(contactRequest.getPhoneNumber())
                .name(contactRequest.getName())
                .user(userRepository.findByEmail(jwtService.extractUsername(jwt)).orElseThrow())
                .build();
        contactRepository.save(contact);
        return ContactResponse.builder()
                .userId(contact.getUser().getId())
                .phoneNumber(contact.getPhoneNumber())
                .name(contact.getName())
                .id(contact.getId())
                .build();
    }

    public ContactResponse getContact(Integer id, String jwt) {
        var user = userRepository.findByEmail(jwtService.extractUsername(jwt)).orElseThrow();
        var contact = contactRepository.findById(id).orElseThrow();
        if (user.equals(contact.getUser())) {
            return ContactResponse.builder()
                    .userId(contact.getUser().getId())
                    .phoneNumber(contact.getPhoneNumber())
                    .name(contact.getName())
                    .id(contact.getId())
                    .build();
        }
        return null;
    }

    public AllContactsResponse getAllContacts(String jwt) {
        var user = userRepository.findByEmail(jwtService.extractUsername(jwt)).orElseThrow();
        List<Optional<Contact>> contacts = contactRepository.findAllByUser(user);
        List<ContactResponse> contactResponses = new ArrayList<>();
        for (var contact : contacts) {
            var unboxedContact = contact.orElseThrow();
            contactResponses.add(ContactResponse.builder()
                    .phoneNumber(unboxedContact.getPhoneNumber())
                    .name(unboxedContact.getName())
                    .id(unboxedContact.getId())
                    .userId(unboxedContact.getUser().getId())
                    .build());
        }
        AllContactsResponse allContactsResponse = new AllContactsResponse(contactResponses);

        return allContactsResponse;
    }

    public ContactResponse deleteContact(Integer id, String jwt) {
        var user = userRepository.findByEmail(jwtService.extractUsername(jwt)).orElseThrow();
        var contact = contactRepository.findById(id).orElseThrow();
        if (user.equals(contact.getUser())) {
            contactRepository.deleteById(id);
            return ContactResponse.builder()
                    .userId(contact.getUser().getId())
                    .phoneNumber(contact.getPhoneNumber())
                    .name(contact.getName())
                    .id(contact.getId())
                    .build();
        }
        return null;
    }

    public ContactResponse updateContact(Integer id, String jwt, ContactRequest contactRequest) {
        var user = userRepository.findByEmail(jwtService.extractUsername(jwt)).orElseThrow();
        var contact = contactRepository.findById(id).orElseThrow();
        if (user.equals(contact.getUser())) {
            contact.setName(contactRequest.getName());
            contact.setPhoneNumber(contactRequest.getPhoneNumber());
            contactRepository.save(contact);
            return ContactResponse.builder()
                    .userId(contact.getUser().getId())
                    .phoneNumber(contact.getPhoneNumber())
                    .name(contact.getName())
                    .build();
        }
        return null;
    }
}
