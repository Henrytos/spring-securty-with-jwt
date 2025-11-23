package com.henry.spring_securty_with_jwt.modules.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticateController {

    private final JpaUserRepository jpaUserRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sing-up")
    public ResponseEntity<Object> singUp(
            @RequestBody SingUpRequestDTO singUpRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Boolean userExists = this.jpaUserRepository.findByEmail(singUpRequestDTO.email()).isPresent();

        if (userExists)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponseDTO("já existe usuario com este email"));


        UserEntity user = UserEntity.builder()
                .email(singUpRequestDTO.email())
                .password(passwordEncoder.encode(singUpRequestDTO.password()))
                .build();

        user = this.jpaUserRepository.save(user);
        URI uri = uriComponentsBuilder.path("/api/v1/{userId}").buildAndExpand(user.getId().toString()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    record SingUpRequestDTO(String email, String password) {
    }

    record MessageResponseDTO(String message) {
    }

}
