package com.petkpetk.service.domain.shopping.dto.review;

import com.petkpetk.service.domain.shopping.dto.review.response.ReviewResponse;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.Data;

@Data
public class ReviewDto {
	private Long id;
	private String content;
	private Long likes;
	private Item item;
	private UserAccount userAccount;

	public ReviewDto(String content, Long likes, Item item, UserAccount userAccount) {
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccount = userAccount;
	}

	public static ReviewDto of(ReviewResponse reviewResponse) {
		return new ReviewDto(reviewResponse.getContent(), reviewResponse.getLikes(), reviewResponse.getItem(), reviewResponse.getUserAccount());
	}
}
