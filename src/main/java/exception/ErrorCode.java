package exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 예시
    INVALID_INPUT(400, "C001", "잘못된 입력입니다."),
    UNAUTHORIZED(401, "C002", "인증이 필요합니다.");

    private final int status;
    private final String code;
    private final String message;
}