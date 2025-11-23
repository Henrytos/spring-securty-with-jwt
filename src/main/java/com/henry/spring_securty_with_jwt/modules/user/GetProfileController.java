package com.henry.spring_securty_with_jwt.modules.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
public class GetProfileController {

    @GetMapping
    public ResponseEntity<Object> me(){
        return null;
    }

}
