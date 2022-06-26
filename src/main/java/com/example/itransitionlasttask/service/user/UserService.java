package com.example.itransitionlasttask.service.user;


import com.example.itransitionlasttask.dto.response.ApiResponse;
import com.example.itransitionlasttask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


}
