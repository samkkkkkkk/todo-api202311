package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPractice
        extends JpaRepository<Todo, String> {
}
