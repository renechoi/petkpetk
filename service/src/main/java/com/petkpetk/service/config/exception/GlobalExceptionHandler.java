package com.petkpetk.service.config.exception;

import static com.petkpetk.service.config.exception.GlobalErrorCode.*;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {



	// @ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(GlobalException.class)
	@ResponseBody
	public GlobalErrorResponse handleException(
		GlobalException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			e.getGlobalErrorCode(), request.getRequestURI(), e.getDetailMessage(),e);

		return GlobalErrorResponse.builder()
			.globalErrorCode(e.getGlobalErrorCode())
			.errorMessage(e.getDetailMessage())
			.build();
	}

	@ExceptionHandler(value = {
		HttpRequestMethodNotSupportedException.class,
		MethodArgumentNotValidException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public GlobalErrorResponse handleBadRequest(
		java.lang.Exception e,
		HttpServletRequest request
	) {
		log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage(), e);
		return GlobalErrorResponse.builder()
			.globalErrorCode(INVALID_REQUEST)
			.errorMessage(INVALID_REQUEST.getMessage())
			.build();
	}


	@ExceptionHandler(java.lang.Exception.class)
	@ResponseBody
	public GlobalErrorResponse handleException(
		java.lang.Exception e,
		HttpServletRequest request
	) {
		log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage(), e);
		return GlobalErrorResponse.builder()
			.globalErrorCode(INTERNAL_SERVER_ERROR)
			.errorMessage(INTERNAL_SERVER_ERROR.getMessage())
			.build();
	}
}
