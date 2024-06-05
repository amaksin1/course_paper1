package com.example.phone_book_online.models.contact;

import com.example.phone_book_online.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findById(Integer id);
    List<Optional<Contact>> findAllByUser(User user);
}
