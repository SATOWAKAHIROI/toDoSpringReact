package com.example.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.request.TodoRequest;
import com.example.backend.dto.response.TodoResponse;
import com.example.backend.service.TodoService;

import jakarta.validation.Valid;
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

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<TodoResponse>>> findListByUser(@PathVariable Long userId) {
        List<TodoResponse> todoList = todoService.getAllByCreatedBy(userId);
        return ResponseEntity.ok(ApiResponse.success(todoList));
    }

    @GetMapping("/{userId}/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponse>> findById(@PathVariable Long userId, @PathVariable Long todoId) {
        TodoResponse todoResponse = todoService.getByUserIdAndTodoId(userId, todoId);

        if (todoResponse == null) {
            return ResponseEntity.ok(ApiResponse.error("404", "findById: 取得失敗"));
        }

        return ResponseEntity.ok(ApiResponse.success(todoResponse));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<TodoResponse>> create(@Valid @RequestBody TodoRequest todoRequest,
            @PathVariable Long userId) {
        TodoResponse todoResponse = todoService.createTodo(todoRequest, userId);

        if (todoResponse == null) {
            return ResponseEntity.ok(ApiResponse.error("404", "create: 作成失敗"));
        }
        return ResponseEntity.ok(ApiResponse.success(todoResponse));
    }

    @PutMapping("/{userId}/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponse>> edit(@Valid @RequestBody TodoRequest todoRequest,
            @PathVariable Long userId, @PathVariable Long todoId) {
        TodoResponse todoResponse = todoService.editTodo(todoRequest, userId, todoId);

        if (todoResponse == null) {
            return ResponseEntity.ok(ApiResponse.error("404", "create: 編集失敗"));
        }
        return ResponseEntity.ok(ApiResponse.success(todoResponse));
    }

    @DeleteMapping("/{userId}/{todoId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long userId, @PathVariable Long todoId) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
