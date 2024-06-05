package com.example.phone_book_online.models.invalid_tokens;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvalidTokensRepository extends JpaRepository<InvalidTokens, Integer> {
    Optional<InvalidTokens> findByToken(String token);

}
