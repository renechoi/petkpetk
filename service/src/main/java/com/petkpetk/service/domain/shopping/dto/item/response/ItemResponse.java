package com.petkpetk.service.domain.shopping.dto.item.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.user.entity.UserAccount;

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

	private UserAccount userAccount;

	private List<ItemImageDto> itemImageDtos = new ArrayList<>();

	public Item toEntity() {
		return Item.of(
			this.itemName,
			this.price,
			this.itemAmount,
			this.itemDetail,
			this.itemStatus,
			null, // TODO : null 처리
			this.userAccount
		);
	}

	public ItemResponse(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus, UserAccount userAccount) {
		this.itemName = itemName;
		this.price = price;
		this.itemAmount = itemAmount;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.userAccount = userAccount;
	}

	public static ItemResponse of(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus, UserAccount userAccount) {
		return new ItemResponse(itemName, price, itemAmount, itemDetail, itemStatus, userAccount);
	}

	public static ItemResponse from(Item item) {
		ItemResponse itemResponse = EntityAndDtoConverter.convertToDto(item, ItemResponse.class);
		itemResponse.setItemImageDtos(item.getImages().stream().map(ItemImageDto::from).collect(Collectors.toList()));
		return itemResponse;
	}

	public static ItemResponse of(Item itemEntity, List<ItemImageDto> itemImageDtos) {
		return new ItemResponse(
			itemEntity.getId(),
			itemEntity.getItemName(),
			itemEntity.getPrice(),
			itemEntity.getItemAmount(),
			itemEntity.getItemDetail(),
			itemEntity.getItemStatus(),
			itemEntity.getUserAccount(),
			itemImageDtos
		);
	}

}
