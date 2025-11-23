package com.hackerrank.challenge.service;

import com.hackerrank.challenge.dtos.CategoryDTO;
import com.hackerrank.challenge.dtos.ItemDTO;
import com.hackerrank.challenge.dtos.SellerDTO;
import com.hackerrank.challenge.exception.ResourceNotFoundException;
import com.hackerrank.challenge.mapper.ItemMapper;
import com.hackerrank.challenge.model.Category;
import com.hackerrank.challenge.model.Item;
import com.hackerrank.challenge.model.Seller;
import com.hackerrank.challenge.repository.CategoryRepository;
import com.hackerrank.challenge.repository.ItemRepository;
import com.hackerrank.challenge.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock private ItemRepository itemRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private SellerRepository sellerRepository;
    @Mock private ItemMapper itemMapper;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void getItemById_WhenExists_ShouldReturnDTO() {
        // GIVEN
        Long id = 1L;
        Item mockItem = new Item();
        mockItem.setId(id);
        mockItem.setTitle("Iphone 16 Pro Max");

        when(itemRepository.findById(id)).thenReturn(Optional.of(mockItem));
        when(itemMapper.mapToDTO(mockItem)).thenReturn(new ItemDTO());

        // WHEN
        ItemDTO result = itemService.findItemById(id);

        // THEN
        assertNotNull(result);
        verify(itemRepository).findById(id);
    }

    @Test
    void getIById_WhenNotExists_ShouldThrowException() {
        // GIVEN
        Long id = 999L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> itemService.findItemById(id));
    }

    @Test
    void createItem_WhenSellerDoesNotExist_ShouldCreateNewSeller() {
        // GIVEN
        ItemDTO input = new ItemDTO();
        SellerDTO sellerDTO = new SellerDTO("Tecnologia Lanus", "link");
        input.setSeller(sellerDTO);
        input.setCategory(new CategoryDTO(1L, null));

        Item itemEntity = new Item();

        // Mocks
        when(itemMapper.mapToEntity(input)).thenReturn(itemEntity);
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(sellerRepository.findByName("Tecnologia Lanus")).thenReturn(Optional.empty());
        when(sellerRepository.save(any(Seller.class))).thenAnswer(i -> i.getArguments()[0]);
        when(itemRepository.save(any())).thenReturn(new Item());
        when(itemMapper.mapToDTO(any())).thenReturn(new ItemDTO());

        // WHEN
        itemService.createItem(input);

        // THEN
        verify(sellerRepository).save(any(Seller.class));
    }

    @Test
    void createItem_whenCategoryExists_thenSaveItem() {
        // GIVEN
        ItemDTO inputDTO = new ItemDTO();
        CategoryDTO catDTO = new CategoryDTO(1L, "Celulares");
        inputDTO.setCategory(catDTO);

        Item itemEntity = new Item();
        Item savedItem = new Item();
        savedItem.setId(100L);

        // WHEN
        when(itemMapper.mapToEntity(inputDTO)).thenReturn(itemEntity);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);
        when(itemMapper.mapToDTO(savedItem)).thenReturn(new ItemDTO());

        ItemDTO result = itemService.createItem(inputDTO);

        // THEN
        assertNotNull(result);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void createItem_WhenCategoryDoesNotExist_ShouldThrowException() {
        ItemDTO inputDTO = new ItemDTO();
        inputDTO.setCategory(new CategoryDTO(999L, "Dinosaurio Real"));

        when(itemMapper.mapToEntity(inputDTO)).thenReturn(new Item());
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> itemService.createItem(inputDTO));

        verify(itemRepository, never()).save(any());
    }
}
