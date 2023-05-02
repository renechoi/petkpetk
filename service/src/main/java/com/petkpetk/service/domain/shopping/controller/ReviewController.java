package com.petkpetk.service.domain.shopping.controller;

import java.util.List;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.dto.review.ReviewDto;
import com.petkpetk.service.domain.shopping.dto.review.ReviewImageDto;
import com.petkpetk.service.domain.shopping.dto.review.request.ReviewRegisterRequest;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.service.item.ItemService;
import com.petkpetk.service.domain.shopping.service.review.ReviewService;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.service.UserAccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewController {

	private final ItemService itemService;
	private final UserAccountService userAccountService;
	private final ReviewService reviewService;

	@PostMapping("/new")
	public String addReview(@RequestParam("itemId") Long itemId, Authentication authentication,
		@Valid ReviewRegisterRequest reviewRegisterRequest) {

		String email = authentication.getName();
		Item item = itemService.getItem(itemId);
		UserAccount userAccount = userAccountService.searchUser(email).get();

		reviewRegisterRequest.setUserAccount(userAccount);
		reviewRegisterRequest.setItem(item);
		reviewRegisterRequest.setLikes(0L);

		reviewService.addReview(
			ReviewDto.from(reviewRegisterRequest)
		);

		return "redirect:/item/" + itemId;
	}

	@PostMapping("/delete/{itemId}/{reviewId}")
	public String deleteReview(@PathVariable Long itemId, @PathVariable Long reviewId) {
		System.out.println("itemId = " + itemId);
		System.out.println("reviewId = " + reviewId);
		reviewService.deleteReview(reviewId);
		return "redirect:/item/" + itemId;
	}

	@PostMapping("/modify/{itemId}/{reviewId}")
	public String modifyReview(@PathVariable Long itemId,
		@PathVariable Long reviewId,
		ReviewRegisterRequest reviewRegisterRequest,
		@RequestParam("images") List<MultipartFile> rawImages,
		@RequestParam("imageNames") List<String> imageNames,
		@RequestParam(value = "uniqueImageNames", required = false) List<String> uniqueImageNames
	) {

		for (MultipartFile imagename : rawImages) {
			System.out.println("imagename.getOriginalFilename() = " + imagename.getOriginalFilename());
			System.out.println("imagename.getName() = " + imagename.getName());
			System.out.println("=====================================================");
		}

		System.out.println("rawImages = " + rawImages);
		System.out.println("uniqueImageNames = " + uniqueImageNames);

		reviewRegisterRequest.setImages(rawImages);
		System.out.println("reviewRegisterRequest = " + reviewRegisterRequest);

		IntStream.range(0, imageNames.size())
			.filter(i -> !imageNames.get(i).equals("첨부파일"))
			.forEach(i -> {
					if (uniqueImageNames != null) {
						reviewRegisterRequest.getReviewImageDtos()
							.add(ReviewImageDto.of(imageNames.get(i), uniqueImageNames.get(i)));
					} else{
						reviewRegisterRequest.getReviewImageDtos()
							.add(new ReviewImageDto());
					}
				}
			);
		reviewService.modifyReview(reviewRegisterRequest, reviewId);

		return "redirect:/item/" + itemId;
	}

}
