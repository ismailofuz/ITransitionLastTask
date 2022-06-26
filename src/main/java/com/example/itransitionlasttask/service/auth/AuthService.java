package com.example.itransitionlasttask.service.auth;

import com.example.itransitionlasttask.dto.request.user.UserRegisterDto;
import com.example.itransitionlasttask.dto.response.jwt.JWTokenResponse;
import com.example.itransitionlasttask.dto.request.user.UserLoginDto;
import com.example.itransitionlasttask.exception.BadRequestException;
import com.example.itransitionlasttask.exception.NotFoundException;
import com.example.itransitionlasttask.model.entity.UserEntity;
import com.example.itransitionlasttask.repository.UserRepository;
import com.example.itransitionlasttask.security.JWTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.itransitionlasttask.model.enums.StateEnum.ACTIVE;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTokenProvider jwTokenProvider;


    public JWTokenResponse register(UserRegisterDto userRegisterDto){
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()){
            throw new BadRequestException("User already registered with: " + userRegisterDto.getEmail());
        }
        UserEntity user = modelMapper.map(userRegisterDto, UserEntity.class);
        user.setState(ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = jwTokenProvider.generateAccessToken(user);
        return new JWTokenResponse(OK.value(), OK.name(), token);
    }

    public JWTokenResponse login(UserLoginDto userLoginDto){
        Optional<UserEntity> OpUser = userRepository.findByEmail(userLoginDto.getEmail());
        if (!OpUser.isPresent()){
            throw new NotFoundException("user not found with this email: " + userLoginDto.getEmail());
        }
        UserEntity user = OpUser.get();
        if (passwordEncoder.matches(user.getPassword(), userLoginDto.getPassword())){
            throw new BadRequestException("wrong password!");
        }
        String token = jwTokenProvider.generateAccessToken(user);
        return new JWTokenResponse(OK.value(), OK.name(), token);
    }
}
