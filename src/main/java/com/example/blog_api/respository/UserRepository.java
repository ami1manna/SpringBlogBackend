package com.example.blog_api.respository;

import com.example.blog_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {



}
