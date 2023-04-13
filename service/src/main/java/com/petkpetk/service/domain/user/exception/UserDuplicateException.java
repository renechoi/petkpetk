package com.petkpetk.service.domain.user.exception;

public class UserDuplicateException extends RuntimeException{

    private static final String message = "이미 가입된 회원입니다";
    public UserDuplicateException() {
        super(message);
    }

    public UserDuplicateException(String content) {
        super(message);
    }

    public UserDuplicateException(Long content) {
        super(message);
    }

    

}
