package com.example.itransitionlasttask.controller;


import com.example.itransitionlasttask.dto.request.user.UserRegisterDto;
import com.example.itransitionlasttask.dto.request.user.UserLoginDto;
import com.example.itransitionlasttask.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRegisterDto){
        return ResponseEntity.ok().body(authService.register(userRegisterDto));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto){
        return ResponseEntity.ok(authService.login(userLoginDto));
    }

}
