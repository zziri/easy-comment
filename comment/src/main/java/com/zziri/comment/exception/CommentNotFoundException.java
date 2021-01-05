package com.zziri.comment.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommentNotFoundException extends RuntimeException {
    private static final String MESSAGE = "댓글을 찾지 못했습니다";
    public CommentNotFoundException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
