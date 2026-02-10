package kr.gilmok.common.dto;

import kr.gilmok.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final String status;   // "error" (고정값 유지)
    private final String code;     // "C001"
    private final String message;  // "잘못된 입력입니다."
    private final LocalDateTime timestamp; // 에러 발생 시간 추가 추천

    // ErrorCode 인터페이스를 받아서 변환하는 메서드
    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status("error")
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}