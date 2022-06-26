package com.example.itransitionlasttask.dto.request.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListChangeStateRequestDto {
    private List<ChangeStateRequestDto> list;
}
