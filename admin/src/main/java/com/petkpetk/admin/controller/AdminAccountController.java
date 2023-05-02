package com.petkpetk.admin.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petkpetk.admin.dto.request.AdminSignupRequest;
import com.petkpetk.admin.service.AdminAccountService;
import com.petkpetk.admin.service.EmailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminAccountController {

	private final AdminAccountService adminAccountService;
	private final EmailService emailService;

	@GetMapping("/login")
	public String admin() {
		return "admin-account/login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("adminSignupRequest", new AdminSignupRequest());
		return "admin-account/register";
	}

	@PostMapping("/register")
	public String register(@Valid AdminSignupRequest adminSignupRequest, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			return "admin-account/register";
		}
		adminAccountService.save(adminSignupRequest);

		return "redirect:/";
	}


	@ResponseBody
	@PostMapping("/send-verification-code")
	public boolean sendVerificationCode(@RequestParam String email){
		String verificationCode = emailService.sendVerificationCode(email);
		emailService.saveVerificationCode(email, verificationCode);
		return true;
	}

	@ResponseBody
	@PostMapping("/verify-verification-code")
	public boolean verifyCode(@RequestParam String email, @RequestParam String verificationCode){
		return emailService.verifyCode(email, verificationCode);
	}


	@ResponseBody
	@PostMapping("/check-email")
	public boolean checkEmailDuplicate(@RequestParam String email) {
		return adminAccountService.checkEmailDuplicate(email);
	}

}
