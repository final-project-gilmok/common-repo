package kr.gilmok.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String status;   // "error" 고정
    private String code;     // "U001", "S001" 등 에러 코드
    private String message;  // "잘못된 입력입니다" 등 메시지

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse("error", code, message);
    }
}