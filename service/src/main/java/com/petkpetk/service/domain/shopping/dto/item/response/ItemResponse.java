package com.petkpetk.service.domain.shopping.dto.item.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.entity.item.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
	private Long id;

	@NotBlank(message = "상품명을 입력해주세요.")
	private String itemName;

	@NotNull(message = "가격을 입력해주세요.")
	private Long price;

	@NotNull(message = "재고량을 입력해주세요.")
	private Long itemAmount;
	
	@NotBlank(message = "상품 정보를 입력해주세요.")
	private String itemDetail;

	private ItemStatus itemStatus;

	private List<ItemImageDto> itemImageDtos = new ArrayList<>();

	private List<Long> itemImageIds = new ArrayList<>();

	public Item toEntity() {
		return Item.of(
			this.itemName,
			this.price,
			this.itemAmount,
			this.itemDetail,
			this.itemStatus,
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	public ItemResponse(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus) {
		this.itemName = itemName;
		this.price = price;
		this.itemAmount = itemAmount;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
	}

	public static ItemResponse of(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus) {
		return new ItemResponse(itemName, price, itemAmount, itemDetail, itemStatus);
	}

	public static ItemResponse of(Item item) {
		return ItemResponse.of(item.getItemName(), item.getPrice(), item.getItemAmount(),
			item.getItemDetail(), item.getItemStatus());
	}

	public static ItemResponse of(Item itemEntity, List<ItemImageDto> itemImageDtos,
		List<Long> itemImageIds) {
		return new ItemResponse(
			itemEntity.getId(),
			itemEntity.getItemName(),
			itemEntity.getPrice(),
			itemEntity.getItemAmount(),
			itemEntity.getItemDetail(),
			itemEntity.getItemStatus(),
			itemImageDtos,
			itemImageIds);
	}

}
