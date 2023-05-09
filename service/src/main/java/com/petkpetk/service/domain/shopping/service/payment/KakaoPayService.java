package com.petkpetk.service.domain.shopping.service.payment;

import javax.transaction.Transactional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.petkpetk.service.config.properties.LocalProperty;
import com.petkpetk.service.domain.shopping.dto.payment.KakaoApproveResponse;
import com.petkpetk.service.domain.shopping.dto.payment.KakaoReadyResponse;
import com.petkpetk.service.domain.shopping.dto.payment.PaymentRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

	static final String cid = LocalProperty.getInstance().getKakaoPaymentCid();
	static final String admin_Key = LocalProperty.getInstance().getKakaoPaymentAdminKey();
	private KakaoReadyResponse kakaoReady;
	private final RestTemplate restTemplate;

	public KakaoReadyResponse kakaoPayReady(PaymentRequest paymentRequest) {

		// 카카오페이 요청 양식
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", cid);
		parameters.add("partner_order_id", "가맹점 주문 번호");
		parameters.add("partner_user_id", "가맹점 회원 ID");
		parameters.add("item_name", paymentRequest.getItemName());
		parameters.add("quantity", "1");
		parameters.add("total_amount", paymentRequest.getFinalPaymentPrice());
		parameters.add("tax_free_amount", "10");
		parameters.add("approval_url", "http://localhost:8082/payment/success");
		parameters.add("cancel_url", "http://localhost:8082/payment/cancel");
		parameters.add("fail_url", "http://localhost:8082/payment/fail");

		// 파라미터, 헤더
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		// 외부에 보낼 url
		kakaoReady = restTemplate.postForObject(
			"https://kapi.kakao.com/v1/payment/ready", requestEntity, KakaoReadyResponse.class);

		// todo : 결제 엔티티 db에 저장 - kakaoPayment

		return kakaoReady;

	}


	/**
	 * 결제 완료 승인
	 */
	public KakaoApproveResponse approveResponse(String pgToken) {

		// 카카오 요청
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", cid);
		parameters.add("tid", kakaoReady.getTid());
		parameters.add("partner_order_id", "가맹점 주문 번호");
		parameters.add("partner_user_id", "가맹점 회원 ID");
		parameters.add("pg_token", pgToken);

		// 파라미터, 헤더
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		// 외부에 보낼 url
		RestTemplate restTemplate = new RestTemplate();

		KakaoApproveResponse approveResponse = restTemplate.postForObject(
			"https://kapi.kakao.com/v1/payment/approve",
			requestEntity,
			KakaoApproveResponse.class);

		return approveResponse;
	}



	/**
	 * 카카오 요구 헤더값
	 */
	private HttpHeaders getHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();

		String auth = "KakaoAK " + admin_Key;

		httpHeaders.set("Authorization", auth);
		httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		return httpHeaders;
	}
}