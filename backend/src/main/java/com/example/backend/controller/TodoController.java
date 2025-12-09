package com.example.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.request.TodoRequest;
import com.example.backend.dto.response.TodoResponse;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/todo")
public class TodoController {
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<TodoResponse>>> findListByUser(@PathVariable Long userId) {
        // todoリストを取得するロジックは後で作成
        List<TodoResponse> todoList = new ArrayList<>();
        return ResponseEntity.ok(ApiResponse.success(todoList));
    }

    @GetMapping("/{userId}/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponse>> findById(@PathVariable Long userId, @PathVariable Long todoId) {
        // todoを取得するロジックは後で作成
        TodoResponse todoResponse = new TodoResponse();
        return ResponseEntity.ok(ApiResponse.success(todoResponse));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<TodoResponse>> postMethodName(@Valid @RequestBody TodoRequest todoRequest) {
        //todoを作成するロジックは後で作成
        TodoResponse todoResponse = new TodoResponse();
        return ResponseEntity.ok(ApiResponse.success(todoResponse));
    }
    
    
    @PutMapping("/{userId}/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponse>> edit(@Valid @RequestBody TodoRequest todoRequest, @PathVariable Long userId, @PathVariable Long todoId) {
        // todoを編集するロジックは後で作成
        TodoResponse todoResponse = new TodoResponse();
        return ResponseEntity.ok(ApiResponse.success(todoResponse));
    }

    @DeleteMapping("/{userId}/{todoId}")
    public ResponseEntity<ApiResponse<Void>> delete (@PathVariable Long userId, @PathVariable Long todoId){
        // todoを削除するロジックは後で作成
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    
    
}
