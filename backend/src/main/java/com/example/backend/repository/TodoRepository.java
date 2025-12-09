package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.entity.Todo;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    /**
     * 指定したユーザーが登録したTodoの一覧取得
     * 
     * @param createdBy ユーザー
     * @return Todoのリスト
     */
    List<Todo> findByCreatedById(Long userId);

    Optional<Todo> findByIdAndCreatedById(Long toDoId, Long userId);

    @Transactional
    void deleteByIdAndCreatedById(Long toDoId, Long userId);
}
