package com.example.blog_api.entity;

import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50 , nullable = false)
    private String name;

    @Column(length = 50)
    private String slug;

    // This is not Addded Field, usefull when we want to fetch post by category
    @OneToMany(mappedBy = "category" , fetch = FetchType.LAZY)
    private List<Post>  posts = new ArrayList<>();

    public Category() {}

    public Category(Long id, String name, String slug, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public Category(String name, String slug, List<Post> posts) {
        this.name = name;
        this.slug = slug;

    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public List<Post> getPosts() { return posts; }
    public void setPosts(List<Post> posts) { this.posts = posts; }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }


}
