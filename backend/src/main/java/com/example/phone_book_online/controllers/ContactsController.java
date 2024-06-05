package com.example.phone_book_online.controllers;

import com.example.phone_book_online.request_processing.ContactRequest;
import com.example.phone_book_online.response_processing.AllContactsResponse;
import com.example.phone_book_online.response_processing.ContactResponse;
import com.example.phone_book_online.services.ContactsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactsController {
    private final ContactsService contactsService;

    @PostMapping("/add")
    public ResponseEntity<ContactResponse> addContact(@RequestBody @Validated ContactRequest contact, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        log.info(contact);
        String jwt = bearer.substring(7);
        return ResponseEntity.ok(contactsService.register(contact, jwt));
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ContactResponse> getContact(@PathVariable Integer contactId, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String jwt = bearer.substring(7);
        return ResponseEntity.ok(contactsService.getContact(contactId, jwt));
    }

    @GetMapping
    public ResponseEntity<AllContactsResponse> getAllContacts(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String jwt = bearer.substring(7);
//        log.info(bearer);
        return ResponseEntity.ok(contactsService.getAllContacts(jwt));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<ContactResponse> deleteContact(@PathVariable Integer contactId, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String jwt = bearer.substring(7);
        log.info("Delete contact: " + contactId);
        return ResponseEntity.ok(contactsService.deleteContact(contactId, jwt));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Integer contactId, @RequestBody @Validated ContactRequest contact, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String jwt = bearer.substring(7);
        return ResponseEntity.ok(contactsService.updateContact(contactId, jwt, contact));
    }
}
