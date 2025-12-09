package com.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TodoRequest {
    @NotBlank @Size(max = 255)
    private String title;
    
    private boolean completed = false;

    public String getTitle(){
        return this.title;
    }

    public void setTitile(String title){
        this.title = title;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
}
