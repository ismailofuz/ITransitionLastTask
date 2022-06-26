package com.example.itransitionlasttask.repository;

import com.example.itransitionlasttask.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity, Long>{

    @Query("select t from tag t " +
            "where t.item.id = ?1 " +
            "order by t.name asc")
    List<TagEntity> findByItemId(Long itemId);
}
