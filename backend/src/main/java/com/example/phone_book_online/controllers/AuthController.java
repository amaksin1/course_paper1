package com.example.phone_book_online.controllers;

import com.example.phone_book_online.request_processing.AuthRequest;
import com.example.phone_book_online.response_processing.AuthResponse;
import com.example.phone_book_online.services.AuthService;
import com.example.phone_book_online.request_processing.RegRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Log4j2
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleException(
            Throwable throwable
    ) {
        if (throwable instanceof MethodArgumentNotValidException notValidException) {
            var bindingResult = notValidException.getBindingResult();
            StringBuilder result = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                result.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest()
                    .body(result.toString());
        }
        return ResponseEntity.badRequest()
                .body(throwable.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<String> handleNewException(
            Throwable throwable
    ) {
        return ResponseEntity.badRequest()
                .body("This email is already in database");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody @Validated RegRequest request
    ) {

        return ResponseEntity.ok(service.register(request));

    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody @Validated AuthRequest request
    ) {
        log.info(request.toString());
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer
    ) {
        return service.logout(bearer);
    }
}

