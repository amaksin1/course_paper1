package com.example.phone_book_online.response_processing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private String phoneNumber;
    private String name;
    private Integer userId;
    private  Integer id;


}
