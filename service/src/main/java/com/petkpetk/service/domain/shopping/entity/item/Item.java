package com.petkpetk.service.domain.shopping.entity.item;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.common.StatusCode;
import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.exception.OutOfStockException;

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
	private  String itemName;

	@Column(name = "price", nullable = false)
	private Long price;

	@Column(nullable = false)
	private Long itemAmount;

	@Column(nullable = false)
	private String itemDetail;

	@Enumerated(EnumType.STRING)
	private ItemStatus itemStatus;

	private LocalDateTime registeredAt;

	private LocalDateTime updatedAt;

	private Item(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
		this.itemName = itemName;
		this.price = price;
		this.itemAmount = itemAmount;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
	}

	public static Item of(String itemName, Long price, Long itemAmount, String itemDetail, ItemStatus itemStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
		return new Item(itemName, price, itemAmount, itemDetail, itemStatus, registeredAt, updatedAt);
	}


	public void updateItem(ItemResponse itemResponse){
		this.itemName = itemResponse.getItemName();
		this.price = itemResponse.getPrice();
		this.itemAmount = itemResponse.getItemAmount();
		this.itemDetail = itemResponse.getItemDetail();
		this.itemStatus = itemResponse.getItemStatus();
	}

	public void selledItem(Long stockAmount) {
		System.out.println("stockAmount = " + stockAmount);
		System.out.println("this.stockAmount = " + this.itemAmount);

		if (this.itemAmount < stockAmount) {
			throw new OutOfStockException(StatusCode.OUT_OF_STOCK,"(현재 재고 수량 : " + this.itemAmount + ")");
		}
		this.itemAmount -= stockAmount;
	}

	public void addItem(Long stockAmount){
		this.itemAmount += stockAmount;
	}



}
