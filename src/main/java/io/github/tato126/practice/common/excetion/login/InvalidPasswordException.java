package io.github.tato126.practice.common.excetion.login;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
