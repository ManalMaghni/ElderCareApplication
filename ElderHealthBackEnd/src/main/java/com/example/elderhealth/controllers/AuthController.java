package com.example.elderhealth.controllers;

import com.example.elderhealth.dto.LoginRequest;
import com.example.elderhealth.entities.User;
import com.example.elderhealth.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findByLogin(request.getLogin());
        Map<String, String> response = new HashMap<>();
        if (user != null && user.getPassword().equals(request.getPassword())) { // Plain-text password check
            response.put("message", "Login successful");
            response.put("user_id",user.getId()+"");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}