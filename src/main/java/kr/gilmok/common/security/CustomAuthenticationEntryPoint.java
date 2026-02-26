package kr.gilmok.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gilmok.common.dto.ErrorResponse;
import kr.gilmok.common.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final MeterRegistry meterRegistry; // ⭐️ 추가: 메트릭 수집기 주입

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        log.warn("Unauthorized Access Attempt - URI: [{}], Message: [{}]", request.getRequestURI(), authException.getMessage());

        // ✅ [추가] 토큰 검증 실패 (또는 인증 없는 접근) 메트릭 1 증가
        // 프로메테우스에는 token_validation_total{result="failure"} 로 저장됩니다.
        meterRegistry.counter("token.validation", "result", "failure").increment();

        // 401 Unauthorized 응답 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.UNAUTHORIZED);

        if (authException instanceof BadCredentialsException || authException instanceof UsernameNotFoundException) {
            errorResponse = ErrorResponse.of(GlobalErrorCode.INVALID_USER);
        }

        // json 변환하여 body 출력
        String json = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}
