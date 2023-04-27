package com.petkpetk.service.domain.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.petkpetk.service.domain.shopping.service.review.ReviewService;
import com.petkpetk.service.domain.shopping.service.review.likes.ReviewLikesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class APIController {


	private final ReviewService reviewService;
	private final ReviewLikesService reviewLikesService;

	@RequestMapping(value = "/review/like", method = RequestMethod.POST)
	public Boolean like(Long num, Long reviewId, Long likeNum, String userEmail){
		System.out.println("============== num = " + num);
		System.out.println("============== reviewId = " + reviewId);
		System.out.println("============== likeNum = " + likeNum);
		System.out.println("============== userEmail = " + userEmail);


		if (num == 1) {
			reviewService.plusLike(num, reviewId, likeNum);
			reviewLikesService.plusLike(reviewId, userEmail);
			return true;
		} else if (num == -1) {
			reviewService.minusLike(num, reviewId, likeNum);
			reviewLikesService.minusLike(reviewId, userEmail);
			return true;
		} else {
			return false;
		}

	}

}
