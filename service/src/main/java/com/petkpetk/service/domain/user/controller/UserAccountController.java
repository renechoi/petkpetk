package com.petkpetk.service.domain.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.user.dto.request.UserSignupRequest;
import com.petkpetk.service.domain.user.dto.request.UserUpdateRequest;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.entity.ProfileImage;
import com.petkpetk.service.domain.user.entity.UserAccount;
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

	@PostMapping("/update")
	public String update(UserUpdateRequest userUpdateRequest) {
		userAccountService.update(userUpdateRequest);
		return "redirect:/user/information";

	}

	@PostMapping("/my-page/delete")
	public String delete(@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		userAccountService.delete(userAccountPrincipal.toDto());
		return "redirect:/";
	}

	@GetMapping("/information")
	public String informationView(Model model,
		@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		ProfileImage profileImage = userAccountService.getUserProfile(userAccountPrincipal);
		model.addAttribute("profileImage", profileImage);
		model.addAttribute("userAccount",
			userAccountService.getUserUpdateRequestView(userAccountPrincipal));
		return "my-page/user/userMyPage";
	}

	@GetMapping("/reviewHistory")
	public String reviewHistory(Model model, Authentication authentication) {
		String email = authentication.getName();
		UserAccount userAccount = userAccountService.searchUser(email).get();
		return null;
	}
}

