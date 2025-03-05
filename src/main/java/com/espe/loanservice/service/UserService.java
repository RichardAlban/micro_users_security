package com.espe.loanservice.service;

import com.espe.loanservice.model.User;
import com.espe.loanservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false).toList();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        user.setIsSuitable(isUserEligibleForLoan(user));
        double maxLoanAmount = calculateMaxLoanAmount(user);
        user.setMaxLoanAmount(maxLoanAmount);
        return userRepository.save(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public long countUsers() {
        return userRepository.count();
    }

    public void generateRecoveryCode(User user) {
        String recoveryCode = UUID.randomUUID().toString();
        user.setRecoveryCode(recoveryCode);
        userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRecoveryCode(null);
        userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isUserEligibleForLoan(User user) {
        return user.getCreditScore() >= 650 && user.getIncome() >= 12000 && !"desempleado".equalsIgnoreCase(user.getEmploymentStatus());
    }

    public double calculateMaxLoanAmount(User user) {
        if (!isUserEligibleForLoan(user)) {
            return 0;
        }
        return (user.getIncome() * 0.4) + (user.getCreditScore() * 0.1);
        
    }
}
