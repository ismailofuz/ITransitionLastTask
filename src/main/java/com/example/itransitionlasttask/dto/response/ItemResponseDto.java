package com.example.itransitionlasttask.dto.response;

import com.example.itransitionlasttask.model.entity.TagEntity;
import com.example.itransitionlasttask.model.entity.item.ItemEntity;
import com.example.itransitionlasttask.model.entity.item.ItemFieldEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {

    private ItemEntity item;
    private List<TagEntity> tags;
    private List<ItemFieldEntity> fields;

}
