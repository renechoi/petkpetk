package com.petkpetk.service.domain.user.exception;

public class UserNotFoundException extends RuntimeException{

    private static final String message = "해당하는 유저를 찾을 수 없습니다";
    public UserNotFoundException() {
        super(message);
    }

    public UserNotFoundException(String content) {
        super(message);
    }

    public UserNotFoundException(Long content) {
        super(message);
    }

    

}
