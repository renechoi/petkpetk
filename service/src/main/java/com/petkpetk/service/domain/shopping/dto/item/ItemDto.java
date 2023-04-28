package com.petkpetk.service.domain.shopping.dto.item;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.request.ItemRegisterRequest;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

	private Long id;

	private String itemName;

	private Long originalPrice;

	private Double discountRate;

	private Long price;

	private Long itemAmount;

	private String itemDetail;

	private ItemStatus itemStatus;

	private Double totalRating;

	private List<MultipartFile> rawImages = new ArrayList<>();
	private UserAccount userAccount;

	private List<ItemImageDto> itemImageDtos = new ArrayList<>();

	private List<Long> itemImageIds = new ArrayList<>();

	public ItemDto(String itemName, Long originalPrice, Double discountRate, Long price, Long itemAmount, String itemDetail, ItemStatus itemStatus,
		List<MultipartFile> rawImages,
		UserAccount userAccount, Double totalRating) {
		this.itemName = itemName;
		this.originalPrice = originalPrice;
		this.discountRate = discountRate;
		this.price = (long)(originalPrice - originalPrice*discountRate);
		this.itemAmount = itemAmount;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.rawImages = rawImages;
		this.userAccount = userAccount;
		this.totalRating = totalRating;
	}

	public static ItemDto of(String itemName, Long originalPrice, Double discountRate, Long price, Long itemAmount, String itemDetail, ItemStatus itemStatus,
		List<MultipartFile> rawImages, UserAccount userAccount, Double totalRating) {
		return new ItemDto(itemName, originalPrice, discountRate, (long)(originalPrice - originalPrice*discountRate), itemAmount, itemDetail, itemStatus, rawImages, userAccount, totalRating);
	}


	public static ItemDto from(Item item){
		return EntityAndDtoConverter.convertToDto(item, ItemDto.class);
	}

	public Item toEntity(List<ItemImage> images) {
		return Item.of(
			this.itemName,
			this.originalPrice,
			this.discountRate,
			(long)(this.originalPrice - this.originalPrice*this.discountRate),
			this.itemAmount,
			this.itemDetail,
			this.itemStatus,
			images,
			this.userAccount,
			this.totalRating
		);
	}

	public Item toEntity() {
		return Item.of(
			this.itemName,
			this.originalPrice,
			this.discountRate,
			(long)(this.originalPrice - this.originalPrice*this.discountRate),
			this.itemAmount,
			this.itemDetail,
			this.itemStatus,
			null,
			this.userAccount,
			this.totalRating
		);
	}


	public static ItemDto from(ItemRegisterRequest itemRegisterRequest, UserAccount userAccount) {
		return new ItemDto(
			itemRegisterRequest.getItemName(),
			itemRegisterRequest.getOriginalPrice(),
			itemRegisterRequest.getDiscountRate(),
			itemRegisterRequest.getPrice(),
			itemRegisterRequest.getItemAmount(),
			itemRegisterRequest.getItemDetail(),
			itemRegisterRequest.getItemStatus(),
			itemRegisterRequest.getImages(),
			userAccount,
			itemRegisterRequest.getTotalRating()
		);
	}
}
