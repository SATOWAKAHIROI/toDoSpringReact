package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** 
 * ユーザーエンティティ
 * システムを使用するユーザー情報を管理
*/
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @PrePersist
    protected void onCreate(){
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updated_at = LocalDateTime.now();
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public LocalDateTime getCreatedAt(){
        return this.created_at;
    }

    public void setCreatedAt(LocalDateTime created_at){
        this.created_at = created_at;
    }

    public LocalDateTime getUpdatedAt(){
        return this.updated_at;
    }

    public void setUpdatedAt(LocalDateTime updated_at){
        this.updated_at = updated_at;
    }
}
