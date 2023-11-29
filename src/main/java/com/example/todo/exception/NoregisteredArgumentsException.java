package com.example.todo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoregisteredArgumentsException
    extends RuntimeException { // 에러를 상속 받아야 한다.


    // 기본 생성자 + 에러메세지를 받는 생성자
    public NoregisteredArgumentsException(String message) {
        super(message);
    }


}












