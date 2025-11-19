-- Database Initialization
drop database blog;
CREATE  DATABASE  blog;
USE blog;

CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  password VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL,     -- <--- Simple & effective
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  slug VARCHAR(50)
);

CREATE TABLE post (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  author_id BIGINT,
  category_id BIGINT,
  title VARCHAR(255),
  slug VARCHAR(255),
  content TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (author_id) REFERENCES users(id),
  FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE comment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  post_id BIGINT,
  author_id BIGINT,
  content TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (post_id) REFERENCES post(id),
  FOREIGN KEY (author_id) REFERENCES users(id)
);


-- Data seeding
-- ========================================
-- BLOG DATABASE SEED DATA (FINAL VERSION)
-- ========================================

-- USERS (10 USERS : 1 ADMIN + 9 VIEWERS)
INSERT INTO users (name, password, role, created_at) VALUES
('Amit Manna', 'password123', 'ROLE_ADMIN', NOW()),
('John Viewer', 'viewerpass', 'ROLE_VIEWER', NOW()),
('Priya Sharma', 'priya123', 'ROLE_VIEWER', NOW()),
('Rohit Kumar', 'rohit123', 'ROLE_VIEWER', NOW()),
('Sneha Patel', 'sneha123', 'ROLE_VIEWER', NOW()),
('Michael Brown', 'mike123', 'ROLE_VIEWER', NOW()),
('Sophia Wilson', 'sophia123', 'ROLE_VIEWER', NOW()),
('David Johnson', 'david123', 'ROLE_VIEWER', NOW()),
('Emma Davis', 'emma123', 'ROLE_VIEWER', NOW()),
('Ryan Taylor', 'ryan123', 'ROLE_VIEWER', NOW());

-- CATEGORIES (10 CATEGORIES)
INSERT INTO category (name, slug) VALUES
('Technology', 'technology'),
('Programming', 'programming'),
('Lifestyle', 'lifestyle'),
('Travel', 'travel'),
('Finance', 'finance'),
('Food', 'food'),
('Health', 'health'),
('Education', 'education'),
('Sports', 'sports'),
('Entertainment', 'entertainment');

-- POSTS (10 POSTS — All created by ADMIN user id = 1)
INSERT INTO post (author_id, category_id, title, slug, content, created_at) VALUES
(1, 1, 'Welcome to the Blog Platform', 'welcome-blog',
 'This is the first admin-created post on the platform.', NOW()),

(1, 2, 'Top 10 Java Tips', 'top-10-java-tips',
 'A curated list of useful Java programming tips for developers.', NOW()),

(1, 3, 'Healthy Lifestyle Guide', 'healthy-lifestyle-guide',
 'Simple habits and lifestyle changes for better health.', NOW()),

(1, 4, 'Best Places to Visit in 2025', 'best-travel-2025',
 'A list of top travel destinations for 2025.', NOW()),

(1, 5, 'How to Manage Your Money', 'money-management',
 'Financial planning tips for smart young professionals.', NOW()),

(1, 6, 'Top 5 Indian Dishes You Must Try', 'indian-dishes',
 'Delicious Indian foods that everyone should taste.', NOW()),

(1, 7, 'Daily Fitness Routine', 'daily-fitness-routine',
 'A simple 20-minute workout plan to start your day.', NOW()),

(1, 8, 'Study Tips for Students', 'study-tips',
 'Improve your grades with these simple and effective study tips.', NOW()),

(1, 9, 'Cricket World Cup Predictions', 'cricket-wc-predictions',
 'Who will win the next Cricket World Cup? Expert predictions.', NOW()),

(1, 10, 'Top Movies to Watch This Year', 'top-movies-2025',
 'A list of highly recommended movies for 2025.', NOW());

-- COMMENTS (15 COMMENTS FROM VIEWER USERS)
INSERT INTO comment (post_id, author_id, content, created_at) VALUES
(1, 2, 'Amazing platform! Excited to be here.', NOW()),
(1, 3, 'Great starting post, keep it up!', NOW()),
(2, 4, 'Very helpful Java tips, thanks!', NOW()),
(2, 5, 'Loved the content.', NOW()),
(3, 6, 'Health is important. Nice post!', NOW()),
(3, 7, 'Very useful lifestyle tips.', NOW()),
(4, 8, 'Travel goals! Want to visit these places.', NOW()),
(4, 9, 'Adding these destinations to my bucket list.', NOW()),
(5, 10, 'Super helpful financial advice.', NOW()),
(6, 2, 'Indian food is the best!', NOW()),
(7, 3, 'Motivating workout routine.', NOW()),
(8, 4, 'Good study tips, very helpful.', NOW()),
(9, 5, 'India will win for sure!', NOW()),
(10, 6, 'Great list of movies!', NOW()),
(10, 7, 'I have watched some of these movies already.', NOW());
