package com.example.projectTLearn.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.repository.AuthRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (authRepository.count() >= 10) {
            return;
        }

        String[] names = {
                "Nguyen Van An",
                "Tran Thi Bich",
                "Le Van Cuong",
                "Pham Thi Duyen",
                "Hoang Minh Em",
                "Vo Thanh F",
                "Doan Thi Giao",
                "Bui Van Huy",
                "Nguyen Thi Lan",
                "Truong Minh Nam"
        };

        for (int i = 0; i < 10; i++) {
            String code = "SV" + String.format("%03d", i + 1);

            if (authRepository.findByStudentCode(code) != null) {
                continue;
            }

            StudentModel student = new StudentModel();
            student.setStudentCode(code);
            student.setName(names[i]);
            student.setFull_name(names[i]);
            student.setPasswordHash(passwordEncoder.encode("123456"));
            student.setPhone("0900000" + (i + 100));
            student.setRole(UserModel.Role.STUDENT);
            student.setStautus(UserModel.Stautus.ACTIVE);
            student.setGender(i % 2 == 0 ? UserModel.Gender.MALE : UserModel.Gender.FEMALE);
            student.setClassId(1);
            student.setEnrollmentYear(LocalDate.now().getYear());
            student.setGpa(BigDecimal.valueOf(3.00 + (i * 0.05)));

            authRepository.save(student);
        }
    }
}
