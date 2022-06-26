package com.example.itransitionlasttask.dto.request.item;

import com.example.itransitionlasttask.model.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListItemFieldRequestDto {
    private List<ItemFieldRequestDto> fieldList;
    private List<TagRequestDto> tagList;
}
