package com.example.projectTLearn.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.example.projectTLearn.model.UserModel;
import com.example.projectTLearn.repository.FetchRepository;
import com.example.projectTLearn.repository.SessionRepository;
import com.example.projectTLearn.types.ApiResponse;

@RequestMapping("/fetch")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class FetchUserController {

    private final FetchRepository fetchRepository;
    private final SessionRepository sessionRepository;

    public FetchUserController(FetchRepository fetchRepository, SessionRepository sessionRepository) {
        this.fetchRepository = fetchRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/me")
    public ApiResponse fetchMe(@AuthenticationPrincipal UserModel user) {

        return new ApiResponse<>(true, "Thông tin : " + user.getName(),
                fetchRepository.fetchMe(user.getId()));
    }

    @GetMapping("/logout")
    public ApiResponse logOut(@AuthenticationPrincipal UserModel user) {
        sessionRepository.deleteByUserId(user.getId());
        return new ApiResponse<>(true, "Đăng xuất thành công", "");
    }
}
