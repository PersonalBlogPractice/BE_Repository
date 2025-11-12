package io.github.tato126.practice.common.excetion.login;

/**
 * 로그인 인증 정보가 올바르지 않을 때 발생하는 예외입니다.
 * <p>
 * 이메일이 존재하지 않거나 비밀번호가 일치하지 않을 때 발생합니다.
 * 보안을 위해 이메일과 비밀번호 오류를 구분하지 않고 동일한 메시지를 반환합니다.
 * HTTP 401 (Unauthorized) 응답으로 변환됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * 기본 예외 메시지와 함께 예외를 생성합니다.
     */
    public InvalidCredentialsException() {
        super("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
}
