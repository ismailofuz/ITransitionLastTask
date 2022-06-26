package com.example.itransitionlasttask.repository;

import com.example.itransitionlasttask.model.entity.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByCollectionId(Long collectionId);
}
