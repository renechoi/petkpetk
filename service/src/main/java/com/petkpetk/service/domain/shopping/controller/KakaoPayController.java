package com.petkpetk.service.domain.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.shopping.dto.payment.PaymentRequest;
import com.petkpetk.service.domain.shopping.service.payment.KakaoPayService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;

	@PostMapping("/ready")
	public String readyToKakaoPay(PaymentRequest paymentRequest) {
		 return String.format("redirect:%s", kakaoPayService.kakaoPayReady(paymentRequest).getNext_redirect_pc_url());
	}

}