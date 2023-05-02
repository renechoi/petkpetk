package com.petkpetk.service.domain.shopping.dto.review.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.dto.review.ReviewImageDto;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.entity.review.ReviewImage;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRegisterRequest {

	private Long id;
	private String content;
	private Long likes;
	private Item item;
	private UserAccount userAccount;
	private Double rating;

	private List<MultipartFile> images = new ArrayList<>();
	private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

	public Review toEntity(List<ReviewImage> images) {
		return Review.of(
			this.item,
			this.userAccount,
			this.content,
			this.likes,
			images,
			this.rating
		);
	}

	public ReviewRegisterRequest(String content, Long likes, Item item, UserAccount userAccount) {
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccount = userAccount;
	}

	public static ReviewRegisterRequest of(String content, Long likes, Item item,
		UserAccount userAccount, Double rating) {
		return ReviewRegisterRequest.of(content, likes, item, userAccount, rating);
	}
}
