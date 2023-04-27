package com.petkpetk.service.domain.shopping.dto.review.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.dto.review.ReviewImageDto;
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


	public ReviewResponse(Long id, String content, Long likes, Item item, UserAccount userAccount) {
		this.id = id;
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccount = userAccount;
	}


	public Review toEntity() {
		return Review.of(
			this.item,
			this.userAccount,
			this.content,
			this.likes,
			null
		);
	}

	public static ReviewResponse of(Long id,String content, Long likes, Item item, UserAccount userAccount) {
		return new ReviewResponse(id,content, likes, item, userAccount);
	}

	public static ReviewResponse from(Review review) {
		ReviewResponse reviewResponse = EntityAndDtoConverter.convertToDto(review, ReviewResponse.class);
		reviewResponse.setReviewImageDtos(review.getImages().stream().map(ReviewImageDto::from).collect(Collectors.toList()));
		return reviewResponse;
	}

	public static ReviewResponse of(Review review, List<ReviewImageDto> reviewImageDtos) {
		return new ReviewResponse(
			review.getId(),
			review.getContent(),
			review.getLikes(),
			review.getItem(),
			review.getUserAccount(),
			reviewImageDtos
		);
	}

}
