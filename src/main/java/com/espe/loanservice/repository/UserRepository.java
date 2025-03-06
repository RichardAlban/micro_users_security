package com.espe.loanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.espe.loanservice.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
