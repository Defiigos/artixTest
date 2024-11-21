package com.example.artixtest.repo;

import com.example.artixtest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByUserName(String userName);
}
