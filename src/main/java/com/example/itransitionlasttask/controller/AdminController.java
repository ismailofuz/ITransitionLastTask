package com.example.itransitionlasttask.controller;

import com.example.itransitionlasttask.dto.request.user.ListChangeStateRequestDto;
import com.example.itransitionlasttask.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/")
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/change_role/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addToAdmin(@PathVariable(name = "userId") Long userId){
        return ResponseEntity.ok(adminService.changeRole(userId));
    }

    @PutMapping("/change_state")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeState(@RequestBody ListChangeStateRequestDto changeStateRequestDtos){
        return ResponseEntity.ok(adminService.changeState(changeStateRequestDtos));
    }


    @GetMapping("/get_all_users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }


}
