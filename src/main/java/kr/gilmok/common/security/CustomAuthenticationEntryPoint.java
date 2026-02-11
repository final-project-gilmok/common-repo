package kr.gilmok.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gilmok.common.dto.ErrorResponse;
import kr.gilmok.common.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        // 401 Unauthorized 응답 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.UNAUTHORIZED);

        // json 변환하여 body 출력
        String json = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}
