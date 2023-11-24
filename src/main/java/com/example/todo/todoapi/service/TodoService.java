package com.example.todo.todoapi.service;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.response.TodoDetailResponseDTO;
import com.example.todo.todoapi.dto.response.TodoListReponseDTO;
import com.example.todo.todoapi.entity.Todo;
import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoListReponseDTO create(final TodoCreateRequestDTO requestDTO)
            throws RuntimeException {
        todoRepository.save(requestDTO.toEntity());
        log.info("할 일 저장 완료! 제목: {}", requestDTO.getTitle());

        return retrieve();

    }

    public TodoListReponseDTO retrieve() {
        List<Todo> entityList = todoRepository.findAll();

        List<TodoDetailResponseDTO> dtoList = entityList.stream()
                /*.map(todo -> new TodoDetailResponseDTO(todo))*/
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());

        return TodoListReponseDTO.builder()
                .todos(dtoList)
                .build();
    }


}
