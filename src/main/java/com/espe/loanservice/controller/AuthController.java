package com.espe.loanservice.controller;

import com.espe.loanservice.model.User;
import com.espe.loanservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.espe.loanservice.model.LoginRequest;
import com.espe.loanservice.dto.AuthenticationResponseDTO;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final String ERROR_KEY = "error";

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        try {
            String response = authService.register(user);
            return ResponseEntity.ok(Map.of("message", response));
        } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of(ERROR_KEY, "Error: " + e.getMessage()));
        } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of(ERROR_KEY, "Error inesperado en el registro."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthenticationResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(ERROR_KEY, "Error: " + e.getMessage()));
        } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of(ERROR_KEY, "Error inesperado en el inicio de sesión."));
        }
    }
}
