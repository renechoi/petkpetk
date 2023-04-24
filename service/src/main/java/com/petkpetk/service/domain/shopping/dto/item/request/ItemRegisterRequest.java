package com.petkpetk.service.domain.shopping.dto.item.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRegisterRequest {
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

	private List<MultipartFile> images = new ArrayList<>();

	private List<ItemImageDto> itemImageDtos = new ArrayList<>();


	public Item toEntity(List<ItemImage> images) {
		return Item.of(
			this.itemName,
			this.price,
			this.itemAmount,
			this.itemDetail,
			this.itemStatus,
			images,
			this.userAccount
		);
	}

	public ItemRegisterRequest(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus, UserAccount userAccount) {
		this.itemName = itemName;
		this.price = price;
		this.itemAmount = itemAmount;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.userAccount = userAccount;
	}

	public static ItemRegisterRequest of(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus, UserAccount userAccount) {
		return new ItemRegisterRequest(itemName, price, itemAmount, itemDetail, itemStatus, userAccount);
	}

	public static ItemRegisterRequest of(Item item) {
		return ItemRegisterRequest.of(item.getItemName(), item.getPrice(), item.getItemAmount(),
			item.getItemDetail(), item.getItemStatus(), item.getUserAccount());
	}
}
