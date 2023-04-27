package com.petkpetk.service.domain.shopping.dto.item;

import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

@Data
public class MainItemDto {
    private Long id;
    private String itemName;
    private String itemDetail;
    private ItemStatus itemStatus;
    private String imageUrl;
    private Long price;
    private Long reviewCount;

    @QueryProjection

    public MainItemDto(Long id, String itemName, String itemDetail, ItemStatus itemStatus,
        String imageUrl, Long price, Long reviewCount) {
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.itemStatus = itemStatus;
        this.imageUrl = imageUrl;
        this.price = price;
        this.reviewCount = reviewCount;
    }
}
