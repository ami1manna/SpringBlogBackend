package com.example.blog_api.config;

import com.example.blog_api.entity.Category;
import com.example.blog_api.entity.Comment;
import com.example.blog_api.entity.Post;
import com.example.blog_api.entity.User;
import com.example.blog_api.respository.CategoryRepository;
import com.example.blog_api.respository.CommentRepository;
import com.example.blog_api.respository.PostRepository;
import com.example.blog_api.respository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(
            UserRepository userRepo,
            CategoryRepository categoryRepo,
            PostRepository postRepo,
            CommentRepository commentRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            // ----------------------------------------------------
            //   1) CHECK IF DATABASE ALREADY SEEDED
            // ----------------------------------------------------
            if (userRepo.count() > 0) {
                System.out.println(">>> Seed data already exists. Skipping...");
                return;
            }

            System.out.println(">>> Loading initial seed data...");

            // ----------------------------------------------------
            //   2) USERS
            // ----------------------------------------------------
            User admin = new User("Amit Manna", passwordEncoder.encode("password123"), "ROLE_ADMIN");
            User u2 = new User("John Viewer", passwordEncoder.encode("viewerpass"), "ROLE_VIEWER");
            User u3 = new User("Priya Sharma", passwordEncoder.encode("priya123"), "ROLE_VIEWER");
            User u4 = new User("Rohit Kumar", passwordEncoder.encode("rohit123"), "ROLE_VIEWER");
            User u5 = new User("Sneha Patel", passwordEncoder.encode("sneha123"), "ROLE_VIEWER");
            User u6 = new User("Michael Brown", passwordEncoder.encode("mike123"), "ROLE_VIEWER");
            User u7 = new User("Sophia Wilson", passwordEncoder.encode("sophia123"), "ROLE_VIEWER");
            User u8 = new User("David Johnson", passwordEncoder.encode("david123"), "ROLE_VIEWER");
            User u9 = new User("Emma Davis", passwordEncoder.encode("emma123"), "ROLE_VIEWER");
            User u10 = new User("Ryan Taylor", passwordEncoder.encode("ryan123"), "ROLE_VIEWER");

            userRepo.saveAll(List.of(admin, u2, u3, u4, u5, u6, u7, u8, u9, u10));

            // ----------------------------------------------------
            //   3) CATEGORIES
            // ----------------------------------------------------
            Category c1 = new Category("Technology", "technology");
            Category c2 = new Category("Programming", "programming");
            Category c3 = new Category("Lifestyle", "lifestyle");
            Category c4 = new Category("Travel", "travel");
            Category c5 = new Category("Finance", "finance");
            Category c6 = new Category("Food", "food");
            Category c7 = new Category("Health", "health");
            Category c8 = new Category("Education", "education");
            Category c9 = new Category("Sports", "sports");
            Category c10 = new Category("Entertainment", "entertainment");

            categoryRepo.saveAll(List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));

            // ----------------------------------------------------
            //   4) POSTS — all created by admin user
            // ----------------------------------------------------
            Post p1 = new Post(admin, c1, "Welcome to the Blog Platform", "welcome-blog",
                    "This is the first admin-created post on the platform.");
            Post p2 = new Post(admin, c2, "Top 10 Java Tips", "top-10-java-tips",
                    "A curated list of useful Java programming tips for developers.");
            Post p3 = new Post(admin, c3, "Healthy Lifestyle Guide", "healthy-lifestyle-guide",
                    "Simple habits and lifestyle changes for better health.");
            Post p4 = new Post(admin, c4, "Best Places to Visit in 2025", "best-travel-2025",
                    "A list of top travel destinations for 2025.");
            Post p5 = new Post(admin, c5, "How to Manage Your Money", "money-management",
                    "Financial planning tips for smart young professionals.");
            Post p6 = new Post(admin, c6, "Top 5 Indian Dishes You Must Try", "indian-dishes",
                    "Delicious Indian foods that everyone should taste.");
            Post p7 = new Post(admin, c7, "Daily Fitness Routine", "daily-fitness-routine",
                    "A simple 20-minute workout plan to start your day.");
            Post p8 = new Post(admin, c8, "Study Tips for Students", "study-tips",
                    "Improve your grades with these simple and effective study tips.");
            Post p9 = new Post(admin, c9, "Cricket World Cup Predictions", "cricket-wc-predictions",
                    "Who will win the next Cricket World Cup? Expert predictions.");
            Post p10 = new Post(admin, c10, "Top Movies to Watch This Year", "top-movies-2025",
                    "A list of highly recommended movies for 2025.");

            postRepo.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));

            // ----------------------------------------------------
            //   5) COMMENTS
            // ----------------------------------------------------
            commentRepo.saveAll(List.of(
                    new Comment(p1, u2, "Amazing platform! Excited to be here."),
                    new Comment(p1, u3, "Great starting post, keep it up!"),
                    new Comment(p2, u4, "Very helpful Java tips, thanks!"),
                    new Comment(p2, u5, "Loved the content."),
                    new Comment(p3, u6, "Health is important. Nice post!"),
                    new Comment(p3, u7, "Very useful lifestyle tips."),
                    new Comment(p4, u8, "Travel goals! Want to visit these places."),
                    new Comment(p4, u9, "Adding these destinations to my bucket list."),
                    new Comment(p5, u10, "Super helpful financial advice."),
                    new Comment(p6, u2, "Indian food is the best!"),
                    new Comment(p7, u3, "Motivating workout routine."),
                    new Comment(p8, u4, "Good study tips, very helpful."),
                    new Comment(p9, u5, "India will win for sure!"),
                    new Comment(p10, u6, "Great list of movies!"),
                    new Comment(p10, u7, "I have watched some of these movies already.")
            ));

            System.out.println(">>> Seed data inserted successfully!");
        };
    }
}
