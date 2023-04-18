package com.petkpetk.service.domain.shopping.dto.item;

import java.time.LocalDateTime;

import com.petkpetk.service.domain.user.entity.SellerAccount;

import lombok.Data;

@Data
public class ItemDto {
	private Long id;
	private String itemName;
	private Integer price;
	private String itemDetail;
	private String itemStatus;
	private SellerAccount sellerAccount;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	public ItemDto(String itemName, Integer price, String itemDetail, String itemStatus,
		SellerAccount sellerAccount,
		LocalDateTime registeredAt, LocalDateTime updatedAt) {
		this.itemName = itemName;
		this.price = price;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.sellerAccount = sellerAccount;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
	}

	public static ItemDto of(String itemName, Integer price, String itemDetail, String itemStatus,
		SellerAccount sellerAccount, LocalDateTime registeredAt, LocalDateTime updatedAt) {
		return new ItemDto(itemName, price, itemDetail, itemStatus, sellerAccount, registeredAt,
			updatedAt);
	}
}
