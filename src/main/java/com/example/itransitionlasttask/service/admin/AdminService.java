package com.example.itransitionlasttask.service.admin;

import com.example.itransitionlasttask.dto.request.user.ChangeStateRequestDto;
import com.example.itransitionlasttask.dto.request.user.ListChangeStateRequestDto;
import com.example.itransitionlasttask.dto.response.ApiResponse;
import com.example.itransitionlasttask.exception.NotFoundException;
import com.example.itransitionlasttask.model.entity.UserEntity;
import com.example.itransitionlasttask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.itransitionlasttask.model.enums.RoleEnum.ROLE_ADMIN;
import static com.example.itransitionlasttask.model.enums.RoleEnum.ROLE_USER;
import static com.example.itransitionlasttask.model.enums.StateEnum.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public ApiResponse changeState(ListChangeStateRequestDto changeStateRequestDtos){
        List<ChangeStateRequestDto> changeStateRequestDtoList = changeStateRequestDtos.getList();

        for (ChangeStateRequestDto changeStateRequestDto : changeStateRequestDtoList) {
            Optional<UserEntity> optionalUser = userRepository.findById(changeStateRequestDto.getUserId());
            if (!optionalUser.isPresent()){
                throw new NotFoundException("user not found with this Id: " + changeStateRequestDto.getUserId());
            }
            UserEntity user = optionalUser.get();
            switch (changeStateRequestDto.getState()){
                case 0: {
                    user.setState(ACTIVE);
                    break;
                }
                case 1: {
                    user.setState(BLOCKED);
                    break;
                }
                case 2: {
                    user.setState(DELETED);
                    break;
                }
            }
            userRepository.save(user);
        }
        return new ApiResponse(1, "Successfully!", null);
    }



    public ApiResponse getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();
        return new ApiResponse(1, "Successfully!", userEntities);
    }


    public ApiResponse changeRole(Long userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent() == false){
            throw new NotFoundException("User not fount with this Id: " + userId);
        }
        UserEntity user = optionalUser.get();

        if (user.getRole().equals(ROLE_USER)) user.setRole(ROLE_ADMIN);
        else user.setRole(ROLE_USER);
        userRepository.save(user);
        return new ApiResponse(1, "Successfully!", null);
    }

}
