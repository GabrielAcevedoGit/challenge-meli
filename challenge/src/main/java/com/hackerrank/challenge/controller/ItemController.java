package com.hackerrank.challenge.controller;

import com.hackerrank.challenge.dtos.ItemDTO;
import com.hackerrank.challenge.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findItemById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody ItemDTO itemDTO) {
        ItemDTO newItem = itemService.createItem(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }
}
