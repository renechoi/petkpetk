package com.petkpetk.service.domain.shopping.entity.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.petkpetk.service.common.AuditingFields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "item_image")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@DynamicUpdate
public class ItemImage extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_image_id")
	private Long id;

	private  String imageName;

	private String originalImageName;

	private String imageUrl;

	private String representativeImageYn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	@ToString.Exclude
	private Item item;

	public void updateItemImage(String imageName, String originalImageName, String imageUrl) {
		this.imageName = imageName;
		this.originalImageName = originalImageName;
		this.imageUrl = imageUrl;
	}

	public ItemImage(Item item, String representativeImageYn) {
		this.item = item;
		this.representativeImageYn = representativeImageYn;
	}

	public static ItemImage of(Item item, int num) {
		return new ItemImage(item, num == 0 ? "Y" : "N");
	}



}
