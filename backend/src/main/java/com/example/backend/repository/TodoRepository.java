package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Todo;
import com.example.backend.entity.User;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    /**
     * 指定したユーザーが登録したTodoの一覧取得
     * @param createdBy ユーザー
     * @return Todoのリスト
     */
    List<Todo> findByCreatedBy(User createdBy);
}
