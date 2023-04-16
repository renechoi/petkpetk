package com.petkpetk.service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {

	SUCCESS_CODE("성공적으로 작업을 수행 했습니다."),
	INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
	INVALID_REQUEST("잘못된 요청입니다."),

	USER_DUPLICATE("중복 회원이 존재합니다."),

	USER_NOT_FOUND("요청한 회원을 찾을 수 없습니다."),

	OAUTH2_PROVIDER_ERROR("소셜 로그인 진행중 오류가 발생했습니다. 다시 시도해주세요.")







	;


	private final String message;
}
