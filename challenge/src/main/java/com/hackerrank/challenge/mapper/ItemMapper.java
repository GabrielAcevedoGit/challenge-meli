package com.hackerrank.challenge.mapper;


import com.hackerrank.challenge.dtos.*;
import com.hackerrank.challenge.model.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public ItemDTO mapToDTO(Item item) {
        if (item == null) {
            return null;
        }
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(String.valueOf(item.getId()));
        itemDTO.setTitle(item.getTitle());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setSoldQuantity(item.getSoldQuantity());
        itemDTO.setStockQuantity(item.getStock());
        itemDTO.setCreatedAt(item.getCreatedAt() != null ? item.getCreatedAt().toString() : null);

        itemDTO.setImages(item.getImages() !=null ? item.getImages(): Collections.emptyList());

        itemDTO.setPrice(mapToPriceDTO(item.getPrice()));
        itemDTO.setSeller(mapToSellerDTO(item.getSeller()));
        itemDTO.setCategory(mapToCategoryDTO(item.getCategory()));
        itemDTO.setAttributes(mapToAttributesDTO(item.getAttributes()));
        itemDTO.setCondition(mapToConditionDTO(item.getCondition()));

        return itemDTO;
    }

    public Item mapToEntity(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        Item item = new Item();
        item.setTitle(itemDTO.getTitle());
        item.setDescription(itemDTO.getDescription());
        item.setSoldQuantity(itemDTO.getSoldQuantity() != null ? itemDTO.getSoldQuantity() : 0);
        item.setStock(itemDTO.getStockQuantity());
        item.setImages(itemDTO.getImages() != null ? itemDTO.getImages() : Collections.emptyList());
        item.setAttributes(mapToItemAttributeEntity(itemDTO.getAttributes()));

        if (itemDTO.getPrice() != null) {
            item.setPrice(new Price(Currency.valueOf(itemDTO.getPrice().getCurrency()),  itemDTO.getPrice().getAmount()));
        }

        if (itemDTO.getCondition() != null) {
            item.setCondition(Condition.valueOf(itemDTO.getCondition().getValue()));
        }
        return item;
    }

    private PriceDTO mapToPriceDTO(Price price) {
        if (price == null) {
            return null;
        }
        return new PriceDTO(price.getAmount(),
                            price.getCurrency().getValueCurrency(),
                            price.formattedAmount());
    }
    private SellerDTO mapToSellerDTO(Seller seller) {
        if (seller == null) {
            return null;
        }
        return new SellerDTO(seller.getName(), seller.getPage());
    }
    private CategoryDTO mapToCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(category.getId(), category.getName());
    }
    private List<AttributeDTO> mapToAttributesDTO(List<ItemAttribute> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return Collections.emptyList();
        }
        return attributes.stream()
                .map(attr -> new AttributeDTO(attr.getName(), attr.getValue()))
                .collect(Collectors.toList());
    }
    private ConditionDTO mapToConditionDTO(Condition condition) {
        if (condition == null){
            return null;
        }
        return new ConditionDTO(condition.name(), condition.getDescription());
    }

    private List<ItemAttribute>  mapToItemAttributeEntity(List<AttributeDTO> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return Collections.emptyList();
        }
        return attributes.stream()
                .map(attr -> new ItemAttribute(attr.getName(), attr.getValue()))
                .collect(Collectors.toList());
    }
}
