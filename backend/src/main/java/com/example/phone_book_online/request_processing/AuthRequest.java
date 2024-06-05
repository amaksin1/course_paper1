package com.example.phone_book_online.request_processing;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "Email may not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password may not be empty")
    private String password;
}
