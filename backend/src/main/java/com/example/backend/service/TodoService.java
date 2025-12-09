package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.TodoRequest;
import com.example.backend.dto.response.TodoResponse;
import com.example.backend.entity.Todo;
import com.example.backend.entity.User;
import com.example.backend.repository.TodoRepository;
import com.example.backend.repository.UserRepository;

@Service
public class TodoService {
    private TodoRepository todoRepository;
    private UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    /**
     * 対象Idのユーザーによって作成されたすべてのタスクを取得するメソッド
     * 
     * @param userId 対象Id
     * @return 対象タスクの一覧
     */
    public List<TodoResponse> getAllByCreatedBy(Long userId) {
        List<Todo> todoList = todoRepository.findByCreatedById(userId);

        List<TodoResponse> responseList = todoList.stream().map(TodoResponse::from).collect(Collectors.toList());

        return responseList;
    }

    /**
     * 対象のuseridとtodoIdからタスクを取得するメソッド
     * 
     * @param userId ユーザーId
     * @param todoId タスクのId
     * @return 取得したタスク
     */
    public TodoResponse getByUserIdAndTodoId(Long userId, Long todoId) {
        Todo todo = todoRepository.findByIdAndCreatedById(todoId, userId).orElse(null);

        // エラー処理は後ほど実装
        if (todo == null) {
            return null;
        }

        return TodoResponse.from(todo);
    }

    /**
     * タスクを作成するメソッド
     * 
     * @param todoRequest
     * @param userId
     * @return
     */
    public TodoResponse createTodo(TodoRequest todoRequest, Long userId) {
        Todo todo = new Todo();

        User user = userRepository.findById(userId).orElse(null);

        // エラー処理は後ほど実装
        if (user == null) {
            return null;
        }

        todo.setTitle(todoRequest.getTitle());
        todo.setCompleted(todoRequest.isCompleted());
        todo.setCreatedBy(user);

        todoRepository.save(todo);

        return TodoResponse.from(todo);
    }

    /**
     * 指定されたuserIdとtodoIdから取得したタスクを編集するメソッド
     * 
     * @param todoRequest
     * @param userid
     * @param todoId
     * @return
     */
    public TodoResponse editTodo(TodoRequest todoRequest, Long userid, Long todoId) {
        Todo todo = todoRepository.findByIdAndCreatedById(todoId, userid).orElse(null);

        // エラー処理は後ほど実装
        if (todo == null) {
            return null;
        }

        todo.setTitle(todoRequest.getTitle());
        todo.setCompleted(todoRequest.isCompleted());

        todoRepository.save(todo);

        return TodoResponse.from(todo);
    }

    public void deleteTodo(Long userId, Long todoId) {
        todoRepository.deleteByIdAndCreatedById(todoId, userId);
    }
}
