package com.petkpetk.service.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {

	SUCCESS_CODE("성공적으로 작업을 수행 했습니다."),
	INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
	INVALID_REQUEST("잘못된 요청입니다."),



	;


	private final String message;
}
