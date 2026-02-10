package kr.gilmok.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus(); // e.g., HttpStatus.BAD_REQUEST
    String getCode();           // e.g., "C001"
    String getMessage();        // e.g., "잘못된 입력입니다."
}