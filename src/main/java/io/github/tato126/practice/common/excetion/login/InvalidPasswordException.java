package io.github.tato126.practice.common.excetion.login;

/**
 * 비밀번호가 일치하지 않을 때 발생하는 예외입니다.
 * <p>
 * 로그인 시 입력한 비밀번호가 저장된 암호화된 비밀번호와 일치하지 않을 때 발생합니다.
 * HTTP 401 (Unauthorized) 응답으로 변환됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * 기본 예외 메시지와 함께 예외를 생성합니다.
     */
    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
