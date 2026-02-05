package kr.gilmok.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CustomException extends RuntimeException {
    // ErrorCode Enum은 나중에 팀원들이 채우더라도, 필드 타입은 String이나 Enum으로 열어두면 됨
    // 지금은 단순하게 String code, message로 해두거나, ErrorCode Enum을 빈 껍데기로 만들어도 됨

    private final String code;
    private final String message;

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}