package com.hackerrank.challenge.service;

import com.hackerrank.challenge.dtos.ItemDTO;


public interface ItemService {
    ItemDTO findItemById(Long id);
    ItemDTO createItem(ItemDTO itemDTO);
}
