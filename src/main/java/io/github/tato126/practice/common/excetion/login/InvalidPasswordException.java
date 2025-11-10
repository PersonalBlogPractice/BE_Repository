package io.github.tato126.practice.common.excetion.login;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String email) {
        super("사용자를 찾을 수 없습니다: " + email);
    }

    public InvalidPasswordException() {
        super("사용자를 찾을 수 없습니다.");
    }
}
