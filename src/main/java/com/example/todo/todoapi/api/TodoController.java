package com.example.todo.todoapi.api;


import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.response.TodoListReponseDTO;
import com.example.todo.todoapi.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(
            @Validated @RequestBody TodoCreateRequestDTO requestDTO,
            BindingResult result
    ) {
        if(result.hasErrors()){
            log.warn("DTO 검증 에러 발생: {}", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try {
            TodoListReponseDTO responseDTO = todoService.create(requestDTO);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (RuntimeException e) {
            /*log.info(e.getMessage());*/
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(TodoListReponseDTO
                            .builder()
                            .error(e.getMessage())
                            .build());

        }

    }

    // 할 일 목록 요청
    @GetMapping
    public  ResponseEntity<?> retrieveTodoList() {
        log.info("/api/todos GET request");
        TodoListReponseDTO responseDTO = todoService.retrieve();

        return ResponseEntity.ok().body(responseDTO);
    }


}
