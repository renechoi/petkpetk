package com.petkpetk.service.domain.shopping.dto.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.entity.review.ReviewImage;

import lombok.Data;

@Data
public class ManageReviewDto {
	private Long id;

	private String itemName;
	private ItemStatus itemStatus;
	private String itemImageUrl;
	private Long price;
	private Long totalRating;

	private String reviewContent;
	private Long likes;
	private Long rating;
	private List<ReviewImage> images = new ArrayList<>();
	private LocalDateTime registeredAt;
}
