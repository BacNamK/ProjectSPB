package com.example.projectTLearn.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class AdminModel extends UserModel {
    private boolean super_admin;

    public boolean isSuper_admin() {
        return super_admin;
    }

    public void setSuper_admin(boolean super_admin) {
        this.super_admin = super_admin;
    }

    public AdminModel() {
    }

    public AdminModel(Long id, String name, String password_hash, String full_name, Gender gender, String phone,
            Role role, Stautus stautus, LocalDateTime crated_at, LocalDateTime updated_at, boolean super_admin) {
        super(id, name, password_hash, full_name, gender, phone, role, stautus, crated_at, updated_at);
        this.super_admin = super_admin;
    }

}
