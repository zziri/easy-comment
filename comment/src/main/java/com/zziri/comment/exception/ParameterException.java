package com.zziri.comment.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParameterException extends RuntimeException {
    private static final String MESSAGE = "Paramater가 잘못되었습니다";
    public ParameterException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
