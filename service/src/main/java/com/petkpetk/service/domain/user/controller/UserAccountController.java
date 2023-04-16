package com.petkpetk.service.domain.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
		return "user/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(UserAccountRequest userAccountRequest) {
		userAccountService.save(userAccountRequest.toDto());
		return "/login";
	}

	@GetMapping("/login")
	public String login() {
		return "/login";
	}


	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return "redirect:/";
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



