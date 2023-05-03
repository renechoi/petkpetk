package com.petkpetk.service.domain.shopping.dto.review.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.dto.review.ReviewImageDto;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
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

	private UserAccountDto userAccountDto;

	private Double rating;

	private LocalDateTime modifiedAt;

	private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

	public ReviewResponse(Long id, String content, Long likes, Item item, UserAccountDto userAccountDto,
		Double rating, LocalDateTime modifiedAt) {
		this.id = id;
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccountDto = userAccountDto;
		this.rating = rating;
		this.modifiedAt = modifiedAt;
	}

	public Review toEntity() {
		return Review.of(
			this.item,
			this.userAccountDto,
			this.content,
			this.likes,
			null,
			this.rating
		);
	}

	public static ReviewResponse of(Long id,String content, Long likes, Item item, UserAccountDto userAccount, Double rating, LocalDateTime modifiedAt) {
		return new ReviewResponse(id,content, likes, item, userAccount, rating, modifiedAt);
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
			UserAccountDto.from(review.getUserAccount()),
			review.getRating(),
			review.getModifiedAt(),
			reviewImageDtos
		);
	}

}
