package kr.gilmok.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    // 생성자 직접 구현
    public CustomException(ErrorCode errorCode) {
        // 부모(RuntimeException)한테 에러 메시지 전달 (로그용)
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}