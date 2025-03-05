package com.espe.loanservice.service;

import com.espe.loanservice.model.User;
import com.espe.loanservice.model.LoginRequest;
import com.espe.loanservice.model.BankAccount;
import com.espe.loanservice.config.JwtTokenProvider;
import com.espe.loanservice.dto.AuthenticationResponseDTO;
import com.espe.loanservice.repository.UserRepository;
import com.espe.loanservice.util.idgenerator.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.espe.loanservice.model.Role;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BankAccountService bankAccountService;
    private final UserService userService;

    @Transactional
    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario con el correo " + user.getEmail() + " ya existe");
        }

        user.setIsSuitable(evaluateSuitability(user));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        double maxLoanAmount = userService.calculateMaxLoanAmount(user);
        user.setMaxLoanAmount(maxLoanAmount);

        User savedUser = userRepository.save(user);

        if (user.getRole() != Role.ADMIN) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(new IdGenerator().generateBankAccount());
            bankAccount.setBalance(0.0);
            bankAccount.setUser(savedUser);
            bankAccountService.createBankAccount(bankAccount);
        }

        return "Usuario registrado con éxito";
    }

    private boolean evaluateSuitability(User user) {
        return user.getIncome() >= 1000 && user.getCreditScore() >= 600;
    }

    public AuthenticationResponseDTO login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new AuthenticationResponseDTO(token, user.getId(), user.getRole().name());
    }
}