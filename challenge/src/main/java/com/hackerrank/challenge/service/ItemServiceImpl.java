package com.hackerrank.challenge.service;

import com.hackerrank.challenge.dtos.ItemDTO;
import com.hackerrank.challenge.exception.ResourceNotFoundException;
import com.hackerrank.challenge.mapper.ItemMapper;
import com.hackerrank.challenge.model.Category;
import com.hackerrank.challenge.model.Item;
import com.hackerrank.challenge.model.Seller;
import com.hackerrank.challenge.repository.CategoryRepository;
import com.hackerrank.challenge.repository.ItemRepository;
import com.hackerrank.challenge.repository.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private SellerRepository sellerRepository;

    private ItemMapper itemMapper;

    ItemServiceImpl(ItemRepository itemRepository,
                    CategoryRepository categoryRepository,
                    SellerRepository sellerRepository,
                    ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDTO findItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        return itemMapper.mapToDTO(item);
    }

    @Override
    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO) {
        Item item = itemMapper.mapToEntity(itemDTO);

        if (itemDTO.getCategory() != null && itemDTO.getCategory().getId() != null) {
            Long catId = itemDTO.getCategory().getId();
            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no existe: " + catId));
            item.setCategory(category);
        }

        if (itemDTO.getSeller() != null) {
            String name = itemDTO.getSeller().getName();
            Seller seller = sellerRepository.findByName(name)
                    .orElseGet(() -> {
                        Seller newSeller = new Seller();
                        newSeller.setName(name);
                        newSeller.setPage(itemDTO.getSeller().getPage());
                        return sellerRepository.save(newSeller);
                    });
            item.setSeller(seller);
        }

        Item savedItem = itemRepository.save(item);

        return itemMapper.mapToDTO(savedItem);
    }

}
