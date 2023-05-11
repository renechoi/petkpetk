package com.petkpetk.service.domain.shopping.exception;

import com.petkpetk.service.common.StatusCode;
import com.petkpetk.service.config.exception.PetkpetkServerException;

public class CancellationInProgressException extends PetkpetkServerException {

	private static final StatusCode statusCode = StatusCode.PAY_CANCEL;

	public CancellationInProgressException(){
		super(statusCode);
	}

	public CancellationInProgressException(StatusCode statusCode) {
		super(statusCode);
	}

	public CancellationInProgressException(StatusCode statusCode, String detailMessage) {
		super(statusCode, detailMessage);
	}



}
