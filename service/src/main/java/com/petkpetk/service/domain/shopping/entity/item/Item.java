package com.petkpetk.service.domain.shopping.entity.item;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.community.entity.Article;
import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.ItemDto;
import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.dto.item.request.ItemRegisterRequest;
import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
import com.petkpetk.service.domain.shopping.exception.OutOfStockException;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "item")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Item extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;

	@Column(nullable = false, length = 50)
	private String itemName;

	@Column(nullable = false)
	private Long originalPrice;

	@Column(nullable = false)
	private Double discountRate;

	@Column(name = "price", nullable = false)
	private Long price;

	@Column(nullable = false)
	private Long itemAmount;

	@Column(nullable = false,columnDefinition = "TEXT", length = 5000)
	private String itemDetail;

	@Enumerated(EnumType.STRING)
	private ItemStatus itemStatus;

	@Column(nullable = false)
	private Double totalRating = 5.0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	@ToString.Exclude
	private UserAccount userAccount;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<ItemImage> images;

	private List<ItemImage> addImages(List<ItemImage> images) {
		images.forEach(image -> image.mapWith(this));
		return images;
	}

	public void mapImages(List<ItemImage> images) {
		images.forEach(image -> image.mapWith(this));
	}


	public void addImage(ItemImage image){
		image.mapWith(this);
		this.images.add(image);
	}

	private Item(String itemName,Long originalPrice, Double discountRate, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus, List<ItemImage> images, UserAccount userAccount, Double totalRating) {
		this.itemName = itemName;
		this.originalPrice = originalPrice;
		this.discountRate = discountRate;
		this.price = (long)(originalPrice - originalPrice*discountRate);
		this.itemAmount = itemAmount;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.images = addImages(setRepresentativeImage(images));
		this.userAccount = userAccount;
		this.totalRating = totalRating;
	}

	public static Item of(String itemName, Long originalPrice, Double discountRate, Long price, Long itemAmount, String itemDetail, ItemStatus itemStatus,
		List<ItemImage> images, UserAccountDto userAccountDto, Double totalRating) {
		return new Item(itemName,originalPrice,discountRate, (long)(originalPrice - originalPrice*discountRate), itemAmount, itemDetail, itemStatus, images, userAccountDto.toEntity(), totalRating);
	}

	public static Item from(ItemDto itemDto){
		Item item = EntityAndDtoConverter.convertToEntity(itemDto, Item.class);
		List<ItemImage> itemImages = itemDto.getItemImageDtos().stream().map(ItemImage::from).collect(
			Collectors.toList());
		item.addImages(itemImages);
		return item;

	}

	private List<ItemImage> setRepresentativeImage(List<ItemImage> images) {
		images.get(0).setRepresentativeImageYn("Y");
		return images;
	}

	public void setContents(ItemRegisterRequest itemUpdateRequest) {
		this.itemName = itemUpdateRequest.getItemName();
		this.originalPrice = itemUpdateRequest.getOriginalPrice();
		this.discountRate = itemUpdateRequest.getDiscountRate();
		this.price = (long)(itemUpdateRequest.getOriginalPrice() - itemUpdateRequest.getOriginalPrice()*itemUpdateRequest.getDiscountRate());
		this.itemAmount = itemUpdateRequest.getItemAmount();
		this.itemDetail = itemUpdateRequest.getItemDetail();
		this.itemStatus = itemUpdateRequest.getItemStatus();
	}

	public void updateItem(ItemResponse itemResponse){
		this.itemName = itemResponse.getItemName();
		this.originalPrice = itemResponse.getOriginalPrice();
		this.discountRate = itemResponse.getDiscountRate();
		this.price = (long)(itemResponse.getOriginalPrice() - itemResponse.getOriginalPrice()*itemResponse.getDiscountRate());
		this.itemAmount = itemResponse.getItemAmount();
		this.itemDetail = itemResponse.getItemDetail();
		this.itemStatus = itemResponse.getItemStatus();
	}

	public void removeStock(Long itemAmount) {
		System.out.println("itemAmount = " + itemAmount);
		System.out.println("this.itemAmount = " + this.itemAmount);

		if (this.itemAmount < itemAmount) {
			throw new OutOfStockException("상품의 재고가 부족합니다 (현재 재고 수량 : " + this.itemAmount + ")");
		}
		this.itemAmount -= itemAmount;
	}

	public void addStock(Long itemAmount){
		this.itemAmount += itemAmount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Item item = (Item)o;
		return Objects.equals(id, item.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
