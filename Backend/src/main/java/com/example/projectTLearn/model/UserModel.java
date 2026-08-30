package com.example.projectTLearn.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String password_hash;

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Stautus getStautus() {
        return stautus;
    }

    public void setStautus(Stautus stautus) {
        this.stautus = stautus;
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "Gender", length = 20)
    private Gender gender;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public enum Role {
        STUDENT, LETURER, MODERATOR, ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", length = 20)
    private Role role;

    public enum Stautus {
        ACTIVE, INACTIVE, SUSPENDED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 20)
    private Stautus stautus;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime crated_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public UserModel() {
    }

    public UserModel(Long id, String name, String password_hash, String full_name, Gender gender, String phone,
            Role role, Stautus stautus, LocalDateTime crated_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.password_hash = password_hash;
        this.full_name = full_name;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
        this.stautus = stautus;
        this.crated_at = crated_at;
        this.updated_at = updated_at;
    }

}
