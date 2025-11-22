package com.example.blog_api.security;

import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ApiException;
import com.example.blog_api.respository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthUtil {

    // Get only username
    public static String getLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return auth.getName();
    }

    // Get FULL User entity
    public static User getLoggedInUser(UserRepository userRepo) {

        String username = getLoggedInUsername();

        if (username == null) {
            throw new ApiException("Unauthorized");
        }

        return userRepo.findByName(username)
                .orElseThrow(() -> new ApiException("User not found"));
    }

    // Check if logged-in user is ADMIN
    public static boolean isAdmin(User user) {
        return user.getRole() != null && user.getRole().equals("ROLE_ADMIN");
    }

    // Require ADMIN (throws exception if NOT admin)
    public static void requireAdmin(User user) {
        if (!isAdmin(user)) {
            throw new ApiException("Admin privileges required");
        }
    }
}
