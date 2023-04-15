package com.petkpetk.service.domain.shopping.dto.item;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ItemDto {
	private Long id;
	private String itemName;
	private Integer price;
	private String itemDetail;
	private String itemStatus;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	public ItemDto(String itemName, Integer price, String itemDetail, String itemStatus,
		LocalDateTime registeredAt, LocalDateTime updatedAt) {
		this.itemName = itemName;
		this.price = price;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
	}

	public static ItemDto of(String itemName, Integer price, String itemDetail, String itemStatus,
		LocalDateTime registeredAt, LocalDateTime updatedAt) {
		return new ItemDto(itemName, price, itemDetail, itemStatus, registeredAt, updatedAt);
	}
}
