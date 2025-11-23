package com.henry.spring_securty_with_jwt.modules.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
public class GetProfileController {

    @GetMapping
    public ResponseEntity<Object> me(
            @AuthenticationPrincipal Object email
    ){
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        System.out.println(email);
        return null;
    }

}
