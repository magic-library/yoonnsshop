package com.example.yoonnsshop.domain.items;

import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.domain.items.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@ApiController
@RequestMapping("items")
public class MemberItemController {
    private ItemService itemService;

    @Autowired
    public MemberItemController(ItemService itemService) {
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
}
