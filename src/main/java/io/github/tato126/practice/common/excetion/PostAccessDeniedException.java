package io.github.tato126.practice.common.excetion;

/**
 * 게시글 접근 권한이 없을 때 발생하는 예외입니다.
 * <p>
 * DRAFT 상태의 비공개 게시글에 접근하려고 할 때 발생합니다.
 * HTTP 403 (Forbidden) 응답으로 변환됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public class PostAccessDeniedException extends RuntimeException {
    /**
     * 예외를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public PostAccessDeniedException(String message) {
        super(message);
    }
}
