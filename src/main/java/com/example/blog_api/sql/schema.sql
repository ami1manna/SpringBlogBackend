-- Database Initialization
CREATE  DATABASE  blog;
USE blog;

-- Table 1: user
CREATE TABLE `user`
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- Table 2: user_role (Composite Primary Key for role assignment)
CREATE TABLE user_role
(
    user_id BIGINT,
    `role` VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, `role`),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- Table 3: category
CREATE TABLE category
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    slug VARCHAR(50) NOT NULL UNIQUE
);

-- Table 4: post
CREATE TABLE post
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    author_id BIGINT NOT NULL,
    category_id BIGINT, -- Can be NULL (post may not require a category)
    title VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    content TEXT NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (author_id) REFERENCES `user`(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Table 5: comment
CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL, -- Requires a registered user to comment
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (author_id) REFERENCES `user`(id)
);

-- seed data
