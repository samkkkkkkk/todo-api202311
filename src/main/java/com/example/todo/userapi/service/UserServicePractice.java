package com.example.todo.userapi.service;


import com.example.todo.auth.TokenProvider;
import com.example.todo.userapi.dto.request.LoginRequestDTO;
import com.example.todo.userapi.dto.request.UserRequestSignUpDTO;
import com.example.todo.userapi.dto.response.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.User;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServicePractice {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    public boolean isDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 회원가입 처리
    public UserSignUpResponseDTO create(UserRequestSignUpDTO dto) {
       String email = dto.getEmail();

       if(isDuplicate(email)) {
           throw new RuntimeException("중복된 이메일 입니다,");
       }

       // 패스워드 인코딩
        String encoded = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encoded);

        // dto를 User Entity로 변환해서 저장
        User saved = userRepository.save(dto.toEntity());

        return new UserSignUpResponseDTO(saved);

    }

    // 회원 인증
    public void authenticate(final LoginRequestDTO dto) {

        // 이메일을 통해 회원 정보 조회
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("가입된 회원이 아닙니다.")
                );

        //패스워드 검증
        String rawPassword = dto.getPassword(); // 입력한 비번
        String encodedPassword = user.getPassword(); // DB에 저장된 암호화된 비번

        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }


    }


}
