package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.UserRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse auth = userService.authUser(loginRequest.getEmail(), loginRequest.getPassword());

        // エラー処理実装後削除
        if (auth == null) {
            return ResponseEntity.ok(ApiResponse.error("403", "login: 認証失敗"));
        }

        return ResponseEntity.ok(ApiResponse.success(auth));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);

        // エラー処理実装後削除
        if (user == null) {
            return ResponseEntity.ok(ApiResponse.error("403", "findById: 取得失敗"));
        }

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserRequest userRequest) {
        UserResponse user = userService.createUser(userRequest);

        // エラー処理実装後削除
        if (user == null) {
            return ResponseEntity.ok(ApiResponse.error("403", "create: 作成失敗"));
        }

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> edit(@PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest) {
        UserResponse user = userService.editUserById(userRequest, id);

        // エラー処理実装後削除
        if (user == null) {
            return ResponseEntity.ok(ApiResponse.error("403", "edit: 編集失敗"));
        }

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}