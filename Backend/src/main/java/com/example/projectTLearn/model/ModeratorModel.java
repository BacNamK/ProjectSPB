package com.example.projectTLearn.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "moderators")
@PrimaryKeyJoinColumn(name = "user_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class ModeratorModel extends UserModel {

    @Column(name = "moderator_code", length = 50, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String permission_scope;

    public String getPermission_scope() {
        return permission_scope;
    }

    public void setPermission_scope(String permission_scope) {
        this.permission_scope = permission_scope;
    }

    public ModeratorModel() {
    }

    public ModeratorModel(Long id, String name, String password_hash, String full_name, Gender gender, String phone,
            Role role, Stautus stautus, LocalDateTime crated_at, LocalDateTime updated_at, String permission_scope) {
        super(id, name, password_hash, full_name, gender, phone, role, stautus, crated_at, updated_at);
        this.permission_scope = permission_scope;
    }

}
