package com.petkpetk.service.domain.shopping.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.dto.review.ReviewDto;
import com.petkpetk.service.domain.shopping.dto.review.request.ReviewRequest;
import com.petkpetk.service.domain.shopping.dto.review.response.ReviewResponse;
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
		ReviewRequest reviewRequest, Model model) {

		String email = authentication.getName();

		Item item = itemService.getItem(itemId);
		UserAccount userAccount = userAccountService.searchUser(email).get();

		reviewRequest.setUserAccount(userAccount);
		reviewRequest.setItem(item);
		reviewRequest.setLikes(0L);

		ReviewDto reviewDto = reviewService.addReview(reviewRequest);
		ItemResponse itemResponse = itemService.getItemDetail(itemId);
		List<ReviewResponse> reviewList = reviewService.getReviewList(itemId);

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
	public String modifyReview(@PathVariable Long itemId, @PathVariable Long reviewId, @RequestParam("newReviewContent") String content){
		reviewService.modifyReview(reviewId, content);

		return "redirect:/item/" + itemId;
	}


}
