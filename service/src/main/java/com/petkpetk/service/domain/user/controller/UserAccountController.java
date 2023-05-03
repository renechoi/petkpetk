package com.petkpetk.service.domain.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.dto.review.ReviewImageDto;
import com.petkpetk.service.domain.shopping.dto.review.request.ReviewRegisterRequest;
import com.petkpetk.service.domain.shopping.dto.review.response.ReviewResponse;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.shopping.service.item.ItemService;
import com.petkpetk.service.domain.shopping.service.review.ReviewService;
import com.petkpetk.service.domain.user.dto.request.UserSignupRequest;
import com.petkpetk.service.domain.user.dto.request.UserUpdateRequest;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.entity.ProfileImage;
import com.petkpetk.service.domain.user.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserAccountController {

	private final UserAccountService userAccountService;

	@GetMapping("/sign-up")
	public String signUp(Model model) {
		model.addAttribute("userAccount", new UserSignupRequest());
		return "user/user/sign-up";
	}
	@GetMapping("/seller/sign-up")
	public String sellerSignUp(Model model) {
		model.addAttribute("userAccount", new UserSignupRequest());
		return "user/seller/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(UserSignupRequest userSignupRequest) {
		userAccountService.save(userSignupRequest);
		return "/login";
	}

}

