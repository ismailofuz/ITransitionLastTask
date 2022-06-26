package com.example.itransitionlasttask.service.item;

import com.example.itransitionlasttask.dto.request.item.ItemFieldRequestDto;
import com.example.itransitionlasttask.dto.request.item.ListItemFieldRequestDto;
import com.example.itransitionlasttask.dto.request.item.TagRequestDto;
import com.example.itransitionlasttask.dto.response.ApiResponse;
import com.example.itransitionlasttask.dto.response.ItemResponseDto;
import com.example.itransitionlasttask.exception.BadRequestException;
import com.example.itransitionlasttask.model.entity.TagEntity;
import com.example.itransitionlasttask.model.entity.collection.CollectionEntity;
import com.example.itransitionlasttask.model.entity.collection.FieldEntity;
import com.example.itransitionlasttask.model.entity.item.ItemEntity;
import com.example.itransitionlasttask.model.entity.item.ItemFieldEntity;
import com.example.itransitionlasttask.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final CollectionRepository collectionRepository;
    private final ItemFieldRepository itemFieldRepository;
    private final ItemRepository itemRepository;
    private final FieldRepository fieldRepository;
    private final TagRepository tagRepository;

    public ApiResponse add(Long collectionId, ListItemFieldRequestDto listItemFieldRequestDto){
        Optional<CollectionEntity> optionalCollection = collectionRepository.findById(collectionId);
        if (!optionalCollection.isPresent()){
            throw new BadRequestException("collection not found with this Id: " + collectionId);
        }

        ItemEntity item = new ItemEntity();
        item.setName((String) listItemFieldRequestDto.getFieldList().get(0).getItemField());
        item.setCollection(optionalCollection.get());
        List<TagRequestDto> tagRequestDtos = listItemFieldRequestDto.getTagList();
        tagRequestDtos.forEach(tagRequestDto -> {
            TagEntity tag = new TagEntity();
            tag.setName(tagRequestDto.getName());
            tag.setItem(item);
            tagRepository.save(tag);
        });
        itemRepository.save(item);

        List<FieldEntity> fieldEntities = fieldRepository.findByCollectionId(collectionId);
        for (int i = 0; i < fieldEntities.size(); i++){
            FieldEntity fieldEntity = fieldEntities.get(i);
            ItemFieldRequestDto itemFieldRequestDto = listItemFieldRequestDto.getFieldList().get(i + 1);
            ItemFieldEntity itemFieldEntity = new ItemFieldEntity();
            switch (fieldEntity.getType()){
                case 0:{
                    itemFieldEntity.setType0((Long) itemFieldRequestDto.getItemField());
                    break;
                }
                case 1:{
                    itemFieldEntity.setType1((String) itemFieldRequestDto.getItemField());
                    break;
                }
                case 2:{
                    itemFieldEntity.setType2((Boolean) itemFieldRequestDto.getItemField());
                    break;
                }
                case 3:{
                    itemFieldEntity.setType3((Date) itemFieldRequestDto.getItemField());
                    break;
                }
            }
            itemFieldEntity.setItem(item);
            itemFieldEntity.setFieldEntity(fieldEntity);
            itemFieldRepository.save(itemFieldEntity);
        }
        return new ApiResponse(1, "success", null);
    }


    public ApiResponse getById(Long collectionId, Long itemId){
        Optional<ItemEntity> optionalItemEntity = itemRepository.findById(itemId);
        if (!optionalItemEntity.isPresent()){
            throw new BadRequestException("item not found with this Id: " + itemId);
        }
        ItemEntity item = optionalItemEntity.get();
        List<TagEntity> tags = tagRepository.findByItemId(itemId);
        List<ItemFieldEntity> itemFieldEntityList = new ArrayList<>();
        List<FieldEntity> fieldEntityList = fieldRepository.findByCollectionId(collectionId);
        fieldEntityList.forEach(fieldEntity -> {
//            Optional<ItemFieldEntity> optionalItemField = ;

        });
        ItemResponseDto itemResponseDto = new ItemResponseDto(item, tags, null);
        return new ApiResponse(1, "success", itemResponseDto);
    }

    public ApiResponse getAll(Long collectionId){
        Optional<CollectionEntity> optionalCollection = collectionRepository.findById(collectionId);
        if (!optionalCollection.isPresent()){
            throw new BadRequestException("collection not found with this Id: " + collectionId);
        }

        List<ItemEntity> itemEntities = itemRepository.findByCollectionId(collectionId);

        return new ApiResponse(1, "success", itemEntities);
    }
}
