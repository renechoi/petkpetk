package com.petkpetk.service.domain.shopping.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petkpetk.service.domain.shopping.dto.item.ItemSearchDto;
import com.petkpetk.service.domain.shopping.dto.item.MainItemDto;
import com.petkpetk.service.domain.shopping.service.item.ItemService;
import com.petkpetk.service.domain.shopping.service.review.ReviewService;
import com.petkpetk.service.domain.shopping.service.review.likes.ReviewLikesService;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.exception.UserNotFoundException;
import com.petkpetk.service.domain.user.service.UserAccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class APIController {

	private final UserAccountService userAccountService;
	private final ItemService itemService;
	private final ReviewService reviewService;
	private final ReviewLikesService reviewLikesService;
	private final PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/review/like", method = RequestMethod.POST)
	public Boolean like(Long num, Long reviewId, Long likeNum, String userEmail) {

		if (num == 1) {
			reviewService.plusLike(reviewId, likeNum);
			reviewLikesService.plusLike(reviewId, userEmail);
			return true;
		} else if (num == -1) {
			reviewService.minusLike(reviewId);
			reviewLikesService.minusLike(reviewId, userEmail);
			return true;
		} else {
			return false;
		}

	}

	@RequestMapping("/items")
	@ResponseBody
	public Map<String, Object> getItems(ItemSearchDto itemSearchDto, Optional<Integer> page) {
		Map<String, Object> result = new HashMap<>();

		PageRequest pageRequest = PageRequest.of(page.orElse(0), 12);
		Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageRequest);

		Long itemCount = itemService.getItemCount();

		result.put("result", items.getContent());
		result.put("totalItemCount", itemCount);

		return result;
	}

	@RequestMapping("/checkPass")
	@ResponseBody
	public boolean checkPass(String password, String email) {
		System.out.println("password = " + password);
		System.out.println("email = " + email);
		UserAccount userAccount = userAccountService.searchUser(email).orElseThrow(
			UserNotFoundException::new);

		return userAccount.checkPassword(password, passwordEncoder);

	}

	@RequestMapping("/checkNickName")
	@ResponseBody
	public boolean checkNickName(String nickName, String email) {
		if (email != null) {
			UserAccount userAccount = userAccountService.searchUser(email).orElseThrow(
				UserNotFoundException::new);
			if (userAccount.getNickname().equals(nickName)) {
				return false;
			} else {
				return userAccountService.searchUserByNickName(nickName).isPresent();
			}
		}
			return userAccountService.searchUserByNickName(nickName).isPresent();

	}
	@RequestMapping("/checkEmail")
	@ResponseBody
	public boolean checkEmail(String email) {
			return userAccountService.searchUser(email).isPresent();

	}
}
