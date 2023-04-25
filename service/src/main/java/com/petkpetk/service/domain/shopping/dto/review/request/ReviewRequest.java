package com.petkpetk.service.domain.shopping.dto.review.request;

import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data()
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

	private Long id;
	private String content;
	private Long likes;
	private Item item;
	private UserAccount userAccount;

	// private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();
	//
	// private List<Long> itemImageIds = new ArrayList<>();

	public static ReviewRequest of(Review review) {
		return new ReviewRequest(
			review.getId(),
			review.getContent(),
			review.getLikes(),
			review.getItem(),
			review.getUserAccount()
		);
	}

}
