package com.petkpetk.service.domain.shopping.exception;

public class OutOfStockException extends  RuntimeException{

	public OutOfStockException(String message) {
		super(message);
		System.out.println("message = " + message);

	}
}
