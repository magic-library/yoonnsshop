package com.example.yoonnsshop.domain.items.repository;

import com.example.yoonnsshop.domain.items.entity.Item;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);

    @Query(value = "SELECT i FROM Item i")
    List<Item> findAllWithoutCounter(Pageable pageable);

    // Redis에서 아이템 개수를 가져오는 메소드
    Long getItemCount();

    @Modifying
    @Query("DELETE FROM Item i WHERE i.seq = :id")
    void deleteById(@Param("id") Long id);
}
