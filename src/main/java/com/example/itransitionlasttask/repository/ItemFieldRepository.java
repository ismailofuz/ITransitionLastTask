package com.example.itransitionlasttask.repository;

import com.example.itransitionlasttask.model.entity.item.ItemFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import java.util.List;
import java.util.Optional;

public interface ItemFieldRepository extends JpaRepository<ItemFieldEntity, Long>{



}
