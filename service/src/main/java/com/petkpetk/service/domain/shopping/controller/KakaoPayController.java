package com.petkpetk.service.domain.shopping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.common.StatusCode;
import com.petkpetk.service.domain.shopping.dto.payment.KakaoCancelResponse;
import com.petkpetk.service.domain.shopping.dto.payment.PaymentRequest;
import com.petkpetk.service.domain.shopping.exception.CancellationInProgressException;
import com.petkpetk.service.domain.shopping.exception.PaymentFailException;
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

	/**
	 * 결제 진행 중 취소
	 */
	@GetMapping("/cancel")
	public void cancel() {

		throw new CancellationInProgressException(StatusCode.PAY_CANCEL);
	}

	/**
	 * 결제 실패
	 */
	@GetMapping("/fail")
	public void fail() {

		throw new PaymentFailException(StatusCode.PAY_FAILED);
	}
	/**
	 * 환불
	 */
	@PostMapping("/refund")
	public ResponseEntity refund() {

		KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel();

		return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
	}

}

