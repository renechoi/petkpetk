package com.petkpetk.service.domain.shopping.dto.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.dto.review.request.ReviewRegisterRequest;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.entity.review.ReviewImage;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.Data;

@Data
public class ReviewDto {
	private Long id;
	private String content;
	private Long likes;
	private Item item;
	private UserAccount userAccount;
	private Double rating;

	private List<MultipartFile> rawImages = new ArrayList<>();
	private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();
	private List<Long> reviewImageIds = new ArrayList<>();

	public ReviewDto(String content, Long likes, Item item, UserAccount userAccount,
		List<MultipartFile> rawImages, Double rating) {
		this.content = content;
		this.likes = likes;
		this.item = item;
		this.userAccount = userAccount;
		this.rawImages = rawImages;
		this.rating = rating;
	}

	public static ReviewDto of(String content, Long likes, Item item, UserAccount userAccount,
		List<MultipartFile> rawImages, Double rating) {
		return new ReviewDto(content, likes, item, userAccount, rawImages, rating);
	}

	public static ReviewDto from(Review review) {
		return EntityAndDtoConverter.convertToDto(review, ReviewDto.class);
	}

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

	public Review toEntity() {
		return Review.of(
			this.item,
			this.userAccount,
			this.content,
			this.likes,
			null,
			this.rating
		);
	}

	public static ReviewDto from(ReviewRegisterRequest reviewRegisterRequest) {
		return new ReviewDto(
			reviewRegisterRequest.getContent(),
			reviewRegisterRequest.getLikes(),
			reviewRegisterRequest.getItem(),
			reviewRegisterRequest.getUserAccount(),
			reviewRegisterRequest.getImages(),
			reviewRegisterRequest.getRating()
		);
	}






}
