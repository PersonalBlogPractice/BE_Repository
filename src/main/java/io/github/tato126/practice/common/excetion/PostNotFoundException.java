package io.github.tato126.practice.common.excetion;

/**
 * 게시글을 찾을 수 없을 때 발생하는 예외입니다.
 * <p>
 * 존재하지 않는 게시글 ID로 조회를 시도할 때 발생합니다.
 * HTTP 404 (Not Found) 응답으로 변환됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public class PostNotFoundException extends RuntimeException {
    /**
     * 예외를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public PostNotFoundException(String message) {
        super(message);
    }
}
