package com.example.phone_book_online.services;
import com.example.phone_book_online.models.invalid_tokens.InvalidTokens;
import com.example.phone_book_online.models.invalid_tokens.InvalidTokensRepository;
import com.example.phone_book_online.models.user.Role;
import com.example.phone_book_online.models.user.User;
import com.example.phone_book_online.models.user.UserRepository;
import com.example.phone_book_online.request_processing.AuthRequest;
import com.example.phone_book_online.request_processing.RegRequest;
import com.example.phone_book_online.response_processing.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final InvalidTokensRepository tokensRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);



        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public ResponseEntity<String> logout(String bearer) {

        var jwtToken = InvalidTokens.builder()
                .token(bearer.substring(7))
                .build();

        tokensRepository.save(jwtToken);

        return ResponseEntity.ok("You've been logged out successfully");
    }
}
