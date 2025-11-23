package com.hackerrank.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.challenge.dtos.*;
import com.hackerrank.challenge.exception.ResourceNotFoundException;
import com.hackerrank.challenge.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Test
    void getItem_WhenExists_ShouldReturn200() throws Exception {
        // GIVEN
        Long id = 1L;
        ItemDTO mockResponse = new ItemDTO();
        mockResponse.setId("1");
        mockResponse.setTitle("Iphone 16 Pro Max");

        when(itemService.findItemById(id)).thenReturn(mockResponse);

        // WHEN & THEN
        mockMvc.perform(get("/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Iphone 16 Pro Max"));
    }

    @Test
    void getItem_WhenNotExists_ShouldReturn404() throws Exception {
        // GIVEN
        Long id = 999L;
        when(itemService.findItemById(id)).thenThrow(new ResourceNotFoundException("No existe"));

        // WHEN & THEN
        mockMvc.perform(get("/items/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void createItem_WithValidData_ShouldReturn201() throws Exception {
        ItemDTO validItem = new ItemDTO();
        validItem.setTitle("Samsung Galaxy S25 Ultra 1tb Titanium Black");
        validItem.setDescription("Capacidad y eficiencia con su potente procesador y memoria RAM de 12 GB tu equipo alcanzará un alto rendimiento con gran velocidad de transmisión de contenidos y ejecutará múltiples aplicaciones a la vez sin demoras.");
        validItem.setStockQuantity(10);
        validItem.setSoldQuantity(0);
        validItem.setImages(Collections.singletonList("imagenes.com/img1"));

        validItem.setPrice(new PriceDTO(new BigDecimal("2000"), "USD", null));
        validItem.setSeller(new SellerDTO("Tecnologia Lanus", "link"));
        validItem.setCategory(new CategoryDTO(1L, null));
        validItem.setCondition(new ConditionDTO("NEW", null));

        when(itemService.createItem(any())).thenReturn(validItem);

        mockMvc.perform(post("/items/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validItem)))
                .andExpect(status().isCreated());
    }

    @Test
    void createItem_WithInvalidData_ShouldReturn400() throws Exception {

        ItemDTO invalidItem = new ItemDTO();
        invalidItem.setTitle("");
        invalidItem.setPrice(null);


        mockMvc.perform(post("/items/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.price").exists());
    }
}