package io.github.tato126.practice.common.excetion.login;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("사용자를 찾을 수 없습니다: " + email);
    }

    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }
}
