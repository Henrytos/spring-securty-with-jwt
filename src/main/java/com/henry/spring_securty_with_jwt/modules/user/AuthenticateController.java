package com.henry.spring_securty_with_jwt.modules.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticateController {

    private final JpaUserRepository jpaUserRepository;

    @PostMapping("/sing-up")
    public ResponseEntity<Object> singUp(
            @RequestBody SingUpRequestDTO singUpRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        UserEntity user = UserEntity.builder()
                .email(singUpRequestDTO.email())
                .password(singUpRequestDTO.password())
                .build();

        user = this.jpaUserRepository.save(user);
        URI uri = uriComponentsBuilder.path("/api/v1/{userId}").buildAndExpand(user.getId().toString()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    record SingUpRequestDTO(String email, String password) {
    }

}
