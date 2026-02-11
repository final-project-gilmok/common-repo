package kr.gilmok.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    // 기존에 있던 예시들 이동
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C002", "인증이 필요합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C000", "서버 내부 오류입니다."),
    NOT_ENOUGH_KEY_LENGTH(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "JWT 시크릿은 최소 256비트(32바이트)여야 합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}