package com.megazone.ERPSystem_phase2_Backend.logistics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 400 Bad Request - 유효하지 않은 요청 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * 404 Not Found - 엔티티를 찾을 수 없는 경우 처리
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * 500 Internal Server Error - 예상하지 못한 서버 에러 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGeneralRuntimeException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.: " + ex.getMessage());
    }

}
