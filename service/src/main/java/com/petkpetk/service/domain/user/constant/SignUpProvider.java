package com.petkpetk.service.domain.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SignUpProvider {

	GOOGLE("구글"),
	NAVER("네이버"),
	KAKAO("카카오");

	private final String providerName;

}