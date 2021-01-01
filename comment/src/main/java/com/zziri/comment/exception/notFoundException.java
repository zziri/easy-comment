package com.zziri.comment.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class notFoundException extends RuntimeException {
    private static final String MESSAGE = "객체를 찾지 못했습니다";
    public notFoundException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
