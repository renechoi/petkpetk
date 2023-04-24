package com.petkpetk.service.domain.shopping.dto.review.response;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.petkpetk.service.domain.shopping.dto.review.ReviewImageDto;
import com.petkpetk.service.domain.shopping.dto.review.request.ReviewRequest;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

	private Long id;

	@NotBlank(message = "리뷰를 입력해주세요.")
	private String content;

	private Long likes;

	private Item item;

	private UserAccount userAccount;

	private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

	private List<Long> itemImageIds = new ArrayList<>();

	public ReviewResponse(String content, Long likes, Item item, UserAccount userAccount) {
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccount = userAccount;
	}

	public ReviewResponse(Long id, String content, Long likes, Item item, UserAccount userAccount) {
		this.id = id;
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccount = userAccount;
	}

	public ReviewResponse(String content, Long likes, Item item, UserAccount userAccount,
		List<ReviewImageDto> reviewImageDtos, List<Long> itemImageIds) {
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccount = userAccount;
		this.reviewImageDtos = reviewImageDtos;
		this.itemImageIds = itemImageIds;
	}

	public Review toEntity() {
		return Review.of(
			this.item,
			this.userAccount,
			this.content,
			this.likes
		);
	}

	public static ReviewResponse of(Long id,String content, Long likes, Item item, UserAccount userAccount) {
		return new ReviewResponse(id,content, likes, item, userAccount);
	}

	public static ReviewResponse of(Review review) {
		return ReviewResponse.of(review.getId(),review.getContent(), review.getLikes(), review.getItem(),
			review.getUserAccount());
	}
	public static ReviewResponse of(ReviewRequest reviewRequest) {
		return ReviewResponse.of(reviewRequest.getId(),reviewRequest.getContent(), reviewRequest.getLikes(), reviewRequest.getItem(),
			reviewRequest.getUserAccount());
	}

	public static ReviewResponse of(Review review, List<ReviewImageDto> reviewImageDtos,
		List<Long> reviewImageIds) {
		return new ReviewResponse(
			review.getId(),
			review.getContent(),
			review.getLikes(),
			review.getItem(),
			review.getUserAccount(),
			reviewImageDtos,
			reviewImageIds
		);
	}

}
