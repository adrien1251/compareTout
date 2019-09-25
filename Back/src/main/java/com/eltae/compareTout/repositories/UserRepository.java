package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
}