package com.henry.spring_securty_with_jwt.modules.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.Authenticator;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticateController {

    private final JpaUserRepository jpaUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

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
        URI uri = uriComponentsBuilder.path("/api/v1/users/{userId}").buildAndExpand(user.getId().toString()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/sing-in")
    public ResponseEntity<Object> singIn(
            @RequestBody SingInRequestDTO singInRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(singInRequestDTO.email(), singInRequestDTO.password());

        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        return ResponseEntity.ok(new MessageResponseDTO("usuario autenticado com sucesso"));
    }

    record SingUpRequestDTO(String email, String password) {
    }

    record SingInRequestDTO(String email, String password) {
    }

    record MessageResponseDTO(String message) {
    }

}
