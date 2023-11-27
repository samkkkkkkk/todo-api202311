package com.example.todo.userapi.api;

import com.example.todo.userapi.dto.request.UserRequestSignUpDTO;
import com.example.todo.userapi.dto.response.UserSignUpResponseDTO;
import com.example.todo.userapi.service.UserServicePractice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class UserPracController {

    private final UserServicePractice userServicePractice;

    // 이메일 중복 확인 요청 처리
    // GET: /api/auth/check?email=zzzz@xxx.com
    public ResponseEntity<?> check(String email) {
        if(email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("이메일이 없습니다.");
        }

        boolean resultFlag = userServicePractice.isDuplicate(email);
        log.info("{} 중복?? - {}", email, resultFlag);

        return ResponseEntity.ok().body(resultFlag);
    }

    // 회원가입 요청 처리
    // POST: /api/auth

    @PostMapping
    public ResponseEntity<?> signup(
            @Validated @RequestBody UserRequestSignUpDTO dto,
            BindingResult result
    ) {

        if(result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        try {
            UserSignUpResponseDTO responseDTO = userServicePractice.create(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}
