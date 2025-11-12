package io.github.tato126.practice.common.excetion.login;

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외입니다.
 * <p>
 * 존재하지 않는 이메일이나 사용자 ID로 조회를 시도할 때 발생합니다.
 * HTTP 404 (Not Found) 응답으로 변환됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * 이메일 정보와 함께 예외를 생성합니다.
     *
     * @param email 찾을 수 없는 사용자의 이메일
     */
    public UserNotFoundException(String email) {
        super("사용자를 찾을 수 없습니다: " + email);
    }

    /**
     * 기본 예외 메시지와 함께 예외를 생성합니다.
     */
    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }
}
