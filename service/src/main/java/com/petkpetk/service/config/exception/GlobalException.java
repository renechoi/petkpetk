package com.petkpetk.service.config.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{



	private GlobalErrorCode globalErrorCode;
	private String detailMessage;


	// 기본메세지
	public GlobalException(GlobalErrorCode globalErrorCode) {
		super(globalErrorCode.getMessage());
		this.globalErrorCode = globalErrorCode;
		this.detailMessage = globalErrorCode.getMessage();
	}

	// 디테일 메세지
	public GlobalException(GlobalErrorCode globalErrorCode, String detailMessage) {
		super(detailMessage);
		this.globalErrorCode = globalErrorCode;
		this.detailMessage = detailMessage;
	}
}
