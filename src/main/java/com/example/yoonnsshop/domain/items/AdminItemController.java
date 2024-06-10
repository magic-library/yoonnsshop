package com.example.yoonnsshop.domain.items;

import com.example.yoonnsshop.common.ApiResponse;
import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.domain.items.dto.CreateItemRequestDto;
import com.example.yoonnsshop.domain.items.dto.UpdateItemRequestDto;
import com.example.yoonnsshop.domain.items.entity.Item;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiController
@RequestMapping("admins/items")
public class AdminItemController {
    private ItemService itemService;

    @Autowired
    public AdminItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // TODO: 반환형 통합
    @GetMapping
    public ResponseEntity<List<Item>> getAllItem () {
        List<Item> items = itemService.findAll();
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable("itemId") Long itemId) {
        Optional<Item> optionalItem = itemService.findById(itemId);
        return optionalItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createItem(@RequestBody @Valid CreateItemRequestDto requestDto) {
        Item createdItem = itemService.registerItem(requestDto);
        return ResponseEntity.ok(new ApiResponse(true, createdItem));
    }

    @PatchMapping("{itemId}")
    public ResponseEntity<ApiResponse> updateItem(@PathVariable("itemId") Long itemId, @RequestBody UpdateItemRequestDto request) {
        Item updatedItem = itemService.updateItem(itemId, request);
        return ResponseEntity.ok(new ApiResponse(true, updatedItem));
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok(new ApiResponse(true, "Item deleted successfully"));
    }
}
