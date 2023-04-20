package com.petkpetk.service.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.dto.request.UserAccountRequest;
import com.petkpetk.service.domain.user.dto.response.UserAccountResponse;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.exception.UserNotFoundException;
import com.petkpetk.service.domain.user.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserAccountController {

	private final UserAccountService userAccountService;

	@GetMapping("/sign-up")
	public String signUp(Model model) {
		model.addAttribute("userAccount", new UserAccountRequest());
		return "user/user/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(UserAccountRequest userAccountRequest) {
		userAccountService.save(userAccountRequest.toDto());
		return "/login";
	}

	@GetMapping("/my-page/update")
	public String update(@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal, Model model) {
		UserAccountDto userAccountDto = userAccountService.searchUserDto(userAccountPrincipal.toDto()).orElseThrow(
			UserNotFoundException::new);
		UserAccountResponse userAccountResponse = UserAccountResponse.from(userAccountDto);
		model.addAttribute("userAccount", userAccountResponse);
		return "user/my-page";
	}

	@PostMapping("/my-page/update")
	public String update(UserAccountRequest userAccountRequest) {
		userAccountService.update(userAccountRequest.toDto());
		return "redirect:/";
	}

	@PostMapping("/my-page/delete")
	public String delete(@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		userAccountService.delete(userAccountPrincipal.toDto());
		return "redirect:/";
	}


}



