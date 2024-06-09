package com.example.yoonnsshop.domain.items.repository;

import com.example.yoonnsshop.domain.items.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
//    Optional<Item> save(Item item);
}
