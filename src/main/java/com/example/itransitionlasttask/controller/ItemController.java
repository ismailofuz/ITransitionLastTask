package com.example.itransitionlasttask.controller;

import com.example.itransitionlasttask.dto.request.item.ListItemFieldRequestDto;
import com.example.itransitionlasttask.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/item/")
public class ItemController{

    private final ItemService itemService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/add/{collectionId}")
    public ResponseEntity<?> add(
            @PathVariable(name = "collectionId") Long collectionId,
            @RequestBody ListItemFieldRequestDto listItemFieldRequestDto
            ){
        return ResponseEntity.ok(itemService.add(collectionId, listItemFieldRequestDto));
    }

    @GetMapping("/get/{collectionId}/{itemId}")
    public ResponseEntity<?> getById(
            @PathVariable(name = "collectionId") Long collectionId,
            @PathVariable(name = "itemId") Long itemId
    ) {
       return ResponseEntity.ok(itemService.getById(collectionId, itemId));
    }

    @GetMapping("/get_all/{collectionId}")
    public ResponseEntity<?> getAll(
            @PathVariable(name = "collectionId") Long collectionId
    ) {
       return ResponseEntity.ok(itemService.getAll(collectionId));
    }



}
