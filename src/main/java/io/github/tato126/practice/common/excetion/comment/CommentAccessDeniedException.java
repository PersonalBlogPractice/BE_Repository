package io.github.tato126.practice.common.excetion.comment;

/**
 * 댓글 접근 권한이 없을 때 발생하는 예외입니다.
 * <p>
 * 댓글 작성자가 아닌 사용자가 댓글을 수정하거나 삭제하려고 할 때 발생합니다.
 * HTTP 403 (Forbidden) 응답으로 변환됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public class CommentAccessDeniedException extends RuntimeException {
    /**
     * 예외를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public CommentAccessDeniedException(String message) {
        super(message);
    }
}
