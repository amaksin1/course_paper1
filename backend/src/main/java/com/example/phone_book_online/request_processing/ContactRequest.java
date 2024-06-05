package com.example.phone_book_online.request_processing;

import com.example.phone_book_online.validation.ValidPhoneNumber;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    @ValidPhoneNumber
    @NotEmpty(message = "Phone number may not be empty")
    private String phoneNumber;
    @NotEmpty(message = "Name may not be empty")
    private String name;
}
