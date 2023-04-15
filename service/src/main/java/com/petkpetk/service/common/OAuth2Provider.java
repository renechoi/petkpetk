package com.petkpetk.service.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {

	GOOGLE("구글"),
	NAVER("네이버"),
	KAKAO("카카오");

	private final String providerName;

}