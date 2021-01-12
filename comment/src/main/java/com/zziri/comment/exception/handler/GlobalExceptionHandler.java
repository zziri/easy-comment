package com.zziri.comment.exception.handler;

import com.zziri.comment.exception.CommentNotFoundException;
import com.zziri.comment.exception.ParameterException;
import com.zziri.comment.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error("서버 오류 : {}", ex.getMessage(), ex);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 오류가 발생했습니다");
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("서버 오류 : {}", ex.getMessage(), ex);
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, "HttpMessageNotReadable 예외가 발생했습니다");
    }

    @ExceptionHandler(value = ParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleParameterException(ParameterException ex) {
        log.error("서버 오류 : {}", ex.getMessage(), ex);
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCommentNotFoundException(CommentNotFoundException ex) {
        log.error("서버 오류 : {}", ex.getMessage(), ex);
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
