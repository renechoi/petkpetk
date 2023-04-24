package com.petkpetk.service.domain.shopping.entity.item;

import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.constant.ItemStatus;
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

	@Column(name = "price", nullable = false)
	private Long price;

	@Column(nullable = false)
	private Long itemAmount;

	@Column(nullable = false)
	private String itemDetail;

	@Enumerated(EnumType.STRING)
	private ItemStatus itemStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	@ToString.Exclude
	private UserAccount userAccount;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	// @OneToMany(mappedBy = "item")
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

	private Item(String itemName, Long price, Long itemAmount, String itemDetail,
		ItemStatus itemStatus, List<ItemImage> images, UserAccount userAccount) {
		this.itemName = itemName;
		this.price = price;
		this.itemAmount = itemAmount;
		this.itemDetail = itemDetail;
		this.itemStatus = itemStatus;
		this.images = addImages(setRepresentativeImage(images));
		this.userAccount = userAccount;
	}

	public static Item of(String itemName, Long price, Long itemAmount, String itemDetail, ItemStatus itemStatus,
		List<ItemImage> images, UserAccount userAccount) {
		return new Item(itemName, price, itemAmount, itemDetail, itemStatus, images, userAccount);
	}

	private List<ItemImage> setRepresentativeImage(List<ItemImage> images) {
		images.get(0).setRepresentativeImageYn("Y");
		return images;
	}

}
