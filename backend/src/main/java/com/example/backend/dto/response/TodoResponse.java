package com.example.backend.dto.response;

import com.example.backend.entity.Todo;

public class TodoResponse {
    private Long id;
    private String title;
    private boolean completed;

    public static TodoResponse from(Todo todo){
        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setId(todo.getId());
        todoResponse.setTitle(todo.getTitle());
        todoResponse.setCompleted(todo.getCompleted());
        return todoResponse;
    }
    
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
}
