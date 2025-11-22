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

            // 1) CHECK IF SEEDED
            if (userRepo.count() > 0) {
                System.out.println(">>> Seed data already exists. Skipping...");
                return;
            }

            System.out.println(">>> Loading initial seed data...");

            // ----------------------------------------------------
            //   2) USERS
            // ----------------------------------------------------
            User admin = new User("root", passwordEncoder.encode("pass"), "ROLE_ADMIN");
            User amit = new User("amit", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User priya = new User("Priya Sharma", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User rohit = new User("Rohit Kumar", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User sneha = new User("Sneha Patel", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User michael = new User("Michael Brown", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User sophia = new User("Sophia Wilson", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User david = new User("David Johnson", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User emma = new User("Emma Davis", passwordEncoder.encode("pass"), "ROLE_VIEWER");
            User ryan = new User("Ryan Taylor", passwordEncoder.encode("pass"), "ROLE_VIEWER");

            userRepo.saveAll(List.of(admin, amit, priya, rohit, sneha, michael, sophia, david, emma, ryan));

            // ----------------------------------------------------
            //   3) CATEGORIES
            // ----------------------------------------------------
            Category cTech = new Category("Technology", "technology");
            Category cProg = new Category("Programming", "programming");
            Category cLife = new Category("Lifestyle", "lifestyle");
            Category cTravel = new Category("Travel", "travel");
            Category cFinance = new Category("Finance", "finance");
            Category cFood = new Category("Food", "food");
            Category cHealth = new Category("Health", "health");
            Category cEdu = new Category("Education", "education");
            Category cSports = new Category("Sports", "sports");
            Category cEnt = new Category("Entertainment", "entertainment");

            categoryRepo.saveAll(List.of(cTech, cProg, cLife, cTravel, cFinance, cFood, cHealth, cEdu, cSports, cEnt));

            // ----------------------------------------------------
            //   4) POSTS (Hardcoded Authors)
            // ----------------------------------------------------
            Post p1 = new Post(admin, cTech, "Welcome to the Blog Platform", "welcome-blog",
                    "This is the first post on the platform.");

            Post p2 = new Post(amit, cProg, "Top 10 Java Tips", "top-10-java-tips",
                    "A curated list of useful Java programming tips for developers.");

            Post p3 = new Post(priya, cLife, "Healthy Lifestyle Guide", "healthy-lifestyle-guide",
                    "Simple habits and lifestyle changes for better health.");

            Post p4 = new Post(rohit, cTravel, "Best Places to Visit in 2025", "best-travel-2025",
                    "A list of top travel destinations for 2025.");

            Post p5 = new Post(sneha, cFinance, "How to Manage Your Money", "money-management",
                    "Financial planning tips for smart young professionals.");

            Post p6 = new Post(michael, cFood, "Top 5 Indian Dishes You Must Try", "indian-dishes",
                    "Delicious Indian foods that everyone should taste.");

            Post p7 = new Post(sophia, cHealth, "Daily Fitness Routine", "daily-fitness-routine",
                    "A simple 20-minute workout plan to start your day.");

            Post p8 = new Post(david, cEdu, "Study Tips for Students", "study-tips",
                    "Improve your grades with these simple and effective study tips.");

            Post p9 = new Post(emma, cSports, "Cricket World Cup Predictions", "cricket-wc-predictions",
                    "Who will win the next Cricket World Cup? Expert predictions.");

            Post p10 = new Post(ryan, cEnt, "Top Movies to Watch This Year", "top-movies-2025",
                    "A list of highly recommended movies for 2025.");

            postRepo.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));

            // ----------------------------------------------------
            //   5) COMMENTS (Hardcoded relationships)
            // ----------------------------------------------------
            commentRepo.saveAll(List.of(
                    // Comments on Post 1 (Welcome)
                    new Comment(p1, amit, "Excited to see this platform grow!"),
                    new Comment(p1, priya, "Great start, looking forward to more."),

                    // Comments on Post 2 (Java Tips)
                    new Comment(p2, rohit, "Tip #3 saved my life today, thanks!"),
                    new Comment(p2, admin, "Great compilation, Amit."),
                    new Comment(p2, michael, "Could you do a part 2 on Streams?"),

                    // Comments on Post 3 (Lifestyle)
                    new Comment(p3, sneha, "I need to start drinking more water."),
                    new Comment(p3, david, "Simple but effective advice."),

                    // Comments on Post 4 (Travel)
                    new Comment(p4, emma, "Bali is definitely on my list."),
                    new Comment(p4, ryan, "Don't forget to visit Kyoto!"),
                    new Comment(p4, sophia, "Beautiful photos (if there were any haha)."),

                    // Comments on Post 5 (Finance)
                    new Comment(p5, amit, "Compound interest is magic."),
                    new Comment(p5, admin, "Very important topic for everyone."),

                    // Comments on Post 6 (Food)
                    new Comment(p6, priya, "Butter Chicken is the goat."),
                    new Comment(p6, david, "I prefer Dosa personally."),

                    // Comments on Post 9 (Cricket)
                    new Comment(p9, rohit, "India is going to win, no doubt."),
                    new Comment(p9, ryan, "Australia looks strong too though."),

                    // Comments on Post 10 (Movies)
                    new Comment(p10, sneha, "I loved Oppenheimer."),
                    new Comment(p10, amit, "Adding these to my watchlist.")
            ));

            System.out.println(">>> Seed data inserted successfully!");
        };
    }
}