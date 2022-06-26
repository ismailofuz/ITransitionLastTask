package com.example.itransitionlasttask.service.collection;
import com.example.itransitionlasttask.dto.request.collection.CollectionRequestDto;
import com.example.itransitionlasttask.dto.request.collection.FieldRequestDto;
import com.example.itransitionlasttask.dto.request.collection.ListFieldRequestDto;
import com.example.itransitionlasttask.dto.response.ApiResponse;
import com.example.itransitionlasttask.exception.NotFoundException;
import com.example.itransitionlasttask.model.entity.UserEntity;
import com.example.itransitionlasttask.model.entity.collection.CollectionEntity;
import com.example.itransitionlasttask.model.entity.collection.FieldEntity;
import com.example.itransitionlasttask.repository.CollectionRepository;
import com.example.itransitionlasttask.repository.FieldRepository;
import com.example.itransitionlasttask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ApiResponse getAll(){
        List<CollectionEntity> allCollections = collectionRepository.findAll();
        return new ApiResponse(1, "Successfully!", allCollections);
    }


    public ApiResponse add(Long userId, CollectionRequestDto collectionRequestDto){
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()){
            throw new NotFoundException("User not found with this Id: " + userId);
        }
        UserEntity user = optionalUser.get();
        CollectionEntity collection = modelMapper.map(collectionRequestDto, CollectionEntity.class);
        collection.setUser(user);
        collectionRepository.save(collection);
        return new ApiResponse(1, "success", null);
    }


    public ApiResponse addField(Long collectionId, ListFieldRequestDto listFieldRequestDto){
        Optional<CollectionEntity> optionalCollection = collectionRepository.findById(collectionId);
        if (!optionalCollection.isPresent()){
            throw new NotFoundException("collection not found with this Id: " + collectionId);
        }
        CollectionEntity collection = optionalCollection.get();
        List<FieldRequestDto> fieldRequestDtos = listFieldRequestDto.getList();
        fieldRequestDtos.forEach(fieldRequestDto -> {
            FieldEntity fieldEntity = modelMapper.map(fieldRequestDto, FieldEntity.class);
            fieldEntity.setCollection(collection);
            fieldRepository.save(fieldEntity);
        });
        return new ApiResponse(1, "success", null);
    }


    public ApiResponse getFields(Long collectionId){
        return new ApiResponse(1, "success", fieldRepository.findByCollectionId(collectionId));
    }


    public ApiResponse getTop(){
        List<CollectionEntity> collectionEntityList = collectionRepository.findTop();
        List<CollectionEntity> collectionEntities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i < collectionEntityList.size()){
                collectionEntities.add(collectionEntityList.get(i));
            }
        }
        return new ApiResponse(1, "success", collectionEntities);
    }
}
