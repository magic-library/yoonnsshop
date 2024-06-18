package com.example.yoonnsshop.domain.items;

import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.domain.items.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@ApiController
@RequestMapping("items")
public class MemberItemController {
    private final ItemService itemService;

    @Autowired
    public MemberItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItem () {
        List<Item> items = itemService.findAll();
        return ResponseEntity.ok().body(items);
    }

    // paging 적용
    @GetMapping("/v2")
    public ResponseEntity<Page<Item>> getAllItemV2(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Item> itemPage = itemService.findAll(pageable);
        return ResponseEntity.ok().body(itemPage);
    }

    // redis counter를 사용하는 findAllV2 메서드를 사용하도록 변경
    @GetMapping("/v3")
    public ResponseEntity<Page<Item>> getAllItemV3(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Item> itemPage = itemService.findAllV2(pageable);
        return ResponseEntity.ok().body(itemPage);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable("itemId") Long itemId) {
        Optional<Item> optionalItem = itemService.findById(itemId);
        return optionalItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
