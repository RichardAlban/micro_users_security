package com.espe.loanservice.controller;

import com.espe.loanservice.model.User;
import com.espe.loanservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.espe.loanservice.service.EmailService;
import com.espe.loanservice.dto.RecoveryRequestDTO;
import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.countUsers());
    }

    @PostMapping("/request-password-recovery")
    public ResponseEntity<String> requestPasswordRecovery(@RequestBody RecoveryRequestDTO request) {
        String email = request.getEmail();

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();
        userService.generateRecoveryCode(user);

        try {
            emailService.sendRecoveryEmail(user.getEmail(), user.getRecoveryCode());
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }

        return ResponseEntity.ok("Recovery email sent");
    }

    @PostMapping("/verify-recovery-code")
    public ResponseEntity<String> verifyRecoveryCode(@RequestBody RecoveryRequestDTO recoveryRequest) {
        Optional<User> userOpt = userService.getUserByEmail(recoveryRequest.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();
        if (!user.getRecoveryCode().equals(recoveryRequest.getRecoveryCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid recovery code");
        }

        userService.updatePassword(user, recoveryRequest.getNewPassword());
        return ResponseEntity.ok("Password updated successfully");
    }
}
