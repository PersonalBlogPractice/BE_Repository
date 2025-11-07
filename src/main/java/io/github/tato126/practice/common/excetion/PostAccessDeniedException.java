package io.github.tato126.practice.common.excetion;

public class PostAccessDeniedException extends RuntimeException {
    public PostAccessDeniedException(String message) {
        super(message);
    }
}
