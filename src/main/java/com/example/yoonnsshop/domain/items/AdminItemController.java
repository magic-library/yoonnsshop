package com.example.yoonnsshop.domain.items;

import com.example.yoonnsshop.common.ApiResponse;
import com.example.yoonnsshop.common.exception.*;
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
@RequestMapping("admin/items")
public class AdminItemController {
    private ItemService itemService;

    @Autowired
    public AdminItemController(ItemService itemService) {
        this.itemService = itemService;
    }

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
        try {
            Item createdItem = itemService.registerItem(requestDto);
            return ResponseEntity.ok(new ApiResponse(true, createdItem));
        } catch (InvalidRequestException e) {
            ApiResponse apiResponse = new ApiResponse(false, e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        } catch (DatabaseInsertionException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("{itemId}")
    public ResponseEntity<ApiResponse> updateItem(@PathVariable("itemId") Long itemId, @RequestBody UpdateItemRequestDto request) {
        try {
            Item updatedItem = itemService.updateItem(itemId, request);
            return ResponseEntity.ok(new ApiResponse(true, updatedItem));
        } catch (InvalidRequestException e) {
            ApiResponse apiResponse = new ApiResponse(false, e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DatabaseUpdateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable("itemId") Long itemId) {
        try {
            itemService.deleteItem(itemId);
            return ResponseEntity.ok(new ApiResponse(true, "Item deleted successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DatabaseDeletionException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
