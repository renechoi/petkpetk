package com.petkpetk.service.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.user.dto.request.UserSignupRequest;
import com.petkpetk.service.domain.user.dto.request.UserUpdateRequest;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
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

	@PostMapping("/sign-up")
	public String signUp(UserSignupRequest userSignupRequest) {
		userAccountService.save(userSignupRequest);
		return "/login";
	}

	@GetMapping("/update")
	public String update(@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal, Model model) {
		model.addAttribute("userAccount", userAccountService.getUserUpdateRequestView(userAccountPrincipal));
		return "user/user/update";
	}

	@PostMapping("/update")
	public String update(UserUpdateRequest userUpdateRequest) {
		userAccountService.update(userUpdateRequest);
		return "redirect:/";
	}

	@PostMapping("/my-page/delete")
	public String delete(@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		userAccountService.delete(userAccountPrincipal.toDto());
		return "redirect:/";
	}
}

