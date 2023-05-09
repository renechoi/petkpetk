package com.petkpetk.service.domain.shopping.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petkpetk.service.domain.shopping.dto.payment.KakaoApproveResponse;
import com.petkpetk.service.domain.shopping.dto.payment.KakaoReadyResponse;
import com.petkpetk.service.domain.shopping.dto.payment.PaymentRequest;
import com.petkpetk.service.domain.shopping.service.payment.KakaoPayService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {
	//카카오API는 결제 준비 > 결제 요청 - 결제 준비에서 리턴된 URL로 카카오페이와 연결 > 카카오페이(폰)에서 결제 후 결제 승인 이 순서로 진행

	private final KakaoPayService kakaoPayService;

	@PostMapping("/ready")
	public String readyToKakaoPay(PaymentRequest paymentRequest) {
		 return String.format("redirect:%s", kakaoPayService.kakaoPayReady(paymentRequest).getNext_redirect_pc_url());
	}



	// /**
	//  * 결제 성공
	//  */
	// @GetMapping("/success")
	// public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {
	//
	// 	KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken);
	//
	// 	return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
	// }

	//
	// /**
	//  * 결제 진행 중 취소
	//  */
	// @GetMapping("/cancel")
	// public void cancel() {
	//
	// 	throw new BusinessLogicException(StatusCode.PAY_CANCEL);
	// }
	//
	// /**
	//  * 결제 실패
	//  */
	// @GetMapping("/fail")
	// public void fail() {
	//
	// 	throw new BusinessLogicException(StatusCode.PAY_FAILED);
	// }
}