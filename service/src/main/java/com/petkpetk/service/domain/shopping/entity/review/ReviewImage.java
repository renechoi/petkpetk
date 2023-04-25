package com.petkpetk.service.domain.shopping.entity.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "review_image")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class ReviewImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_image_id")
	private Long id;

	private String imageName;

	private String originalImageName;

	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	@ToString.Exclude
	private Review review;


	public void updateReviewImage(String imageName, String originalImageName, String imageUrl) {
		this.imageName = imageName;
		this.originalImageName = originalImageName;
		this.imageUrl = imageUrl;
	}

}
