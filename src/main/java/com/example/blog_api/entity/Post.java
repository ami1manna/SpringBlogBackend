package com.example.blog_api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(length = 255)
    private String slug;

    @Column(length = 60 , nullable = true)
    private String imagePath;

    @CreationTimestamp
    @Column(name="created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    //  author_id FK
    @ManyToOne(fetch = FetchType.LAZY) // create a foreign key reference userID
    @JoinColumn(name = "author_id") // rename foreign key column name
    private User author;

    // category_id FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;




    // Just Joins Comments Table in this post no seperate refernce Commment id
    @OneToMany(mappedBy = "post" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    // constructor
    public Post() {}

    public Post(Long id, String title, String content, String slug, User author, LocalDateTime createdAt, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.slug = slug;
        this.author = author;
        this.createdAt = createdAt;
        this.comments = comments;
    }

    public Post(User author, Category category, String title, String slug, String content) {
        this.author = author;
        this.category = category;
        this.title = title;
        this.slug = slug;
        this.content = content;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // toString
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", slug='" + slug + '\'' +
                ", author=" + author +
                ", createdAt=" + createdAt +
                ", comments=" + comments +
                '}';
    }
}
