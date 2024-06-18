package com.example.yoonnsshop.domain.items;

import com.example.yoonnsshop.common.exception.DatabaseDeletionException;
import com.example.yoonnsshop.common.exception.DatabaseInsertionException;
import com.example.yoonnsshop.common.exception.DatabaseUpdateException;
import com.example.yoonnsshop.common.exception.ResourceNotFoundException;
import com.example.yoonnsshop.domain.items.dto.CreateItemRequestDto;
import com.example.yoonnsshop.domain.items.dto.UpdateItemRequestDto;
import com.example.yoonnsshop.domain.items.entity.Item;
import com.example.yoonnsshop.domain.items.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final EntityManager entityManager;

    @Autowired
    public ItemService(ItemRepository itemRepository, RedisTemplate<String, String> redisTemplate, EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.redisTemplate = redisTemplate;
        this.entityManager = entityManager;
    }

    private void enableFilter() {
        entityManager.unwrap(Session.class).enableFilter("activeFilter");
    }

    public List<Item> findAll() {
        enableFilter();
        List<Item> items = itemRepository.findAll();
        return items;
    }

    public Page<Item> findAll(Pageable pageable) {
        enableFilter();
        return itemRepository.findAll(pageable); // 페이징된 데이터
    }

    public Page<Item> findAllV2(Pageable pageable) {
        enableFilter();
        Long totalCount = itemRepository.getItemCount(); // Redis에서 가져온 총 개수
        List<Item> items = itemRepository.findAllWithoutCounter(pageable); // 페이징된 데이터
        return new PageImpl<>(items, pageable, totalCount);
    }

    public Optional<Item> findById(Long itemId) {
        enableFilter();
        return itemRepository.findById(itemId);
    }

    @Transactional
    public Item registerItem(CreateItemRequestDto requestDto) {
        enableFilter();
        validateCreateItemRequestDto(requestDto);
        Item item = Item.Builder.anItem()
                .withName(requestDto.getName())
                .withDescription(requestDto.getDescription())
                .withPrice(requestDto.getPrice())
                .withStockQuantity(requestDto.getStockQuantity())
                .build();

        Item savedItem = itemRepository.save(item);
        if (savedItem == null) {
            throw new DatabaseInsertionException("Failed to register item");
        }

        redisTemplate.opsForValue().increment("item_count");
        return savedItem;
    }

    private void validateCreateItemRequestDto(CreateItemRequestDto item) {
        if (item.getName().isEmpty() || item.getPrice() == null) {
            throw new IllegalArgumentException("name and price must be provided");
        }
    }

    @Transactional
    public Item updateItem(Long itemId, UpdateItemRequestDto updateDto) {
        enableFilter();
        // validation check
        validateUpdateItemRequestDto(updateDto);

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));

        Item.Builder builder = Item.Builder.anItem()
                .withSeq(existingItem.getSeq())
                .withName(updateDto.getName() != null ? updateDto.getName() : existingItem.getName())
                .withPrice(updateDto.getPrice() != null ? updateDto.getPrice() : existingItem.getPrice())
                .withDescription(updateDto.getDescription() != null ? updateDto.getDescription() : existingItem.getDescription())
                .withStockQuantity(updateDto.getStockQuantity() != null ? updateDto.getStockQuantity() : existingItem.getStockQuantity());

        return Optional.of(builder.build())
                .map(itemRepository::save)
                .orElseThrow(() -> new DatabaseUpdateException("Failed to update item with id: " + itemId));
    }

    private void validateUpdateItemRequestDto(UpdateItemRequestDto updateDto) {
        if (updateDto.getName().isEmpty()) {
            throw new IllegalArgumentException("name must be provided");
        }
    }

    @Transactional
    public void deleteItem(Long itemId) {
        enableFilter();
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));
        existingItem.deactivate();

        try {
            itemRepository.save(existingItem);
        } catch (Exception e) {
            throw new DatabaseDeletionException("Failed to delete item with id: " + itemId, e);
        }

        redisTemplate.opsForValue().decrement("item_count");
    }

    @Transactional
    public void updateCountCache() {
        Long totalCount = itemRepository.count();
        redisTemplate.opsForValue().set("item_count", totalCount.toString());
    }
}
