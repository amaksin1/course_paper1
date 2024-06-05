package com.example.phone_book_online.request_processing;

import com.example.phone_book_online.validation.ValidPassword;
import com.example.phone_book_online.validation.ValidPhoneNumber;
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
public class RegRequest {
    @NotEmpty(message = "Email may not be empty")
    @Email
    private String email;
    @ValidPassword
    private String password;
}
