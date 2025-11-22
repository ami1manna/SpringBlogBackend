package com.example.blog_api.respository;

import com.example.blog_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByName(String username);


    boolean existsByName(String name);
}
