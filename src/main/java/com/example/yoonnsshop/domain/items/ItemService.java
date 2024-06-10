package com.example.yoonnsshop.domain.items;

import com.example.yoonnsshop.common.exception.DatabaseDeletionException;
import com.example.yoonnsshop.common.exception.DatabaseInsertionException;
import com.example.yoonnsshop.common.exception.DatabaseUpdateException;
import com.example.yoonnsshop.common.exception.ResourceNotFoundException;
import com.example.yoonnsshop.domain.items.dto.CreateItemRequestDto;
import com.example.yoonnsshop.domain.items.dto.UpdateItemRequestDto;
import com.example.yoonnsshop.domain.items.entity.Item;
import com.example.yoonnsshop.domain.items.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll() {
        List<Item> items = itemRepository.findAll();
        return items;
    }

    public Optional<Item> findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public Item registerItem(CreateItemRequestDto requestDto) {
        validateCreateItemRequestDto(requestDto);
        Item item = Item.Builder.anItem()
                .withName(requestDto.getName())
                .withDescription(requestDto.getDescription())
                .withPrice(requestDto.getPrice())
                .withStockQuantity(requestDto.getStockQuantity())
                .build();

        return Optional.of(item)
                .map(itemRepository::save)
                .orElseThrow(() -> new DatabaseInsertionException("Failed to register item"));
    }

    private void validateCreateItemRequestDto(CreateItemRequestDto item) {
        if (item.getName().isEmpty() || item.getPrice() == null) {
            throw new IllegalArgumentException("name and price must be provided");
        }
    }

    public Item updateItem(Long itemId, UpdateItemRequestDto updateDto) {
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

    public void deleteItem(Long itemId) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));

        Optional.of(existingItem)
                .map(item -> {
                    itemRepository.delete(item);
                    return item;
                })
                .orElseThrow(() -> new DatabaseDeletionException("Failed to delete item with id: " + itemId));
    }
}
