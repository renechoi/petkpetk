package com.petkpetk.service.domain.shopping.exception;

import com.petkpetk.service.common.StatusCode;
import com.petkpetk.service.config.exception.PetkpetkServerException;

public class StockAlreadyInCartException extends  PetkpetkServerException{

	private static final StatusCode statusCode = StatusCode.ALREADY_IN_CART;

	public StockAlreadyInCartException(){
		super(statusCode);
	}

	public StockAlreadyInCartException(StatusCode statusCode) {
		super(statusCode);
	}

	public StockAlreadyInCartException(String message) {
		super(statusCode);
	}

	public StockAlreadyInCartException(StatusCode statusCode, String detailMessage) {
		super(statusCode, detailMessage);
	}


}
