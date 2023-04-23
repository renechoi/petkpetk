package com.petkpetk.service.domain.shopping.dto.review;

import com.petkpetk.service.domain.shopping.entity.review.ReviewImage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewImageDto {

	private Long id;

	private  String imageName;

	private  String originalImageName;

	private String imageUrl;

	public ReviewImageDto(String imageName, String originalImageName, String imageUrl) {
		this.imageName = imageName;
		this.originalImageName = originalImageName;
		this.imageUrl = imageUrl;
	}

	public static ReviewImageDto of(ReviewImage reviewImage) {
		return ReviewImageDto.of(reviewImage.getImageName(), reviewImage.getOriginalImageName(),
			reviewImage.getImageUrl());
	}

	public static ReviewImageDto of(String imageName, String originalImageName, String imageUrl){
		return new ReviewImageDto(imageName,originalImageName,imageUrl);
	}

}
