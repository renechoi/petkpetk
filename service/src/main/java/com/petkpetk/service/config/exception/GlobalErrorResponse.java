package com.petkpetk.service.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalErrorResponse {

	private GlobalErrorCode globalErrorCode;
	private String errorMessage;
}
