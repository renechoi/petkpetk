package com.petkpetk.service.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.user.dto.SellerAccountDto;
import com.petkpetk.service.domain.user.dto.request.SellerAccountRequest;
import com.petkpetk.service.domain.user.dto.response.SellerAccountResponse;
import com.petkpetk.service.domain.user.dto.security.SellerAccountPrincipal;
import com.petkpetk.service.domain.user.exception.UserNotFoundException;
import com.petkpetk.service.domain.user.service.SellerAccountService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/seller")
@Controller
@RequiredArgsConstructor
public class SellerAccountController {

	private final SellerAccountService sellerAccountService;

	@GetMapping("/sign-up")
	public String signUp(Model model) {
		model.addAttribute("sellerAccount", new SellerAccountRequest());
		return "seller/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(SellerAccountRequest sellerAccountRequest) {
		sellerAccountService.save(sellerAccountRequest.toDto());
		return "/login";
	}

	@GetMapping("/login")
	public String login() {
		return "/login";
	}

	@GetMapping("/my-page/update")
	public String update(@AuthenticationPrincipal SellerAccountPrincipal sellerAccountPrincipal, Model model) {
		SellerAccountDto sellerAccountDto = sellerAccountService.searchSellerDto(sellerAccountPrincipal.toDto())
			.orElseThrow(UserNotFoundException::new);
		SellerAccountResponse sellerAccountResponse = SellerAccountResponse.from(sellerAccountDto);
		model.addAttribute("sellerAccount", sellerAccountResponse);
		return "seller/my-page";
	}

	@PostMapping("/my-page/update")
	public String update(SellerAccountRequest sellerAccountRequest) {
		sellerAccountService.update(sellerAccountRequest.toDto());
		return "redirect:/";
	}

	@PostMapping("/my-page/delete")
	public String delete(@AuthenticationPrincipal SellerAccountPrincipal sellerAccountPrincipal) {
		sellerAccountService.delete(sellerAccountPrincipal.toDto());
		return "redirect:/";
	}

}
