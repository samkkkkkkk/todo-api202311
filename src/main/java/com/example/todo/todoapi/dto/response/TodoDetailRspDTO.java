package com.example.todo.todoapi.dto.response;

import com.example.todo.todoapi.entity.TodoEntityPractice;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDetailRspDTO {

    private String id;
    private String title;
    private boolean done;


    // 엔터티를 DTO로 만들어 주는 생성자.


    public TodoDetailRspDTO(TodoEntityPractice todo) {
        this.id = todo.getTodoId();
        this.title = todo.getTitile();
        this.done = todo.isDone();
    }
}
