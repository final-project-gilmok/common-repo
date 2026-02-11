package kr.gilmok.common.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gilmok.common.dto.AuthUserDto;
import kr.gilmok.common.security.CustomUserDetails;
import kr.gilmok.common.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Request Header에서 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰 유효성 검사 (common 모듈의 JwtUtils 활용)
        try {
            if (token != null && JwtUtils.validateToken(token, secretKey)) {
                // 3. 토큰이 유효하면 인증 객체 생성
                Authentication authentication = getAuthentication(token);
                // 4. SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다.", authentication.getName());
            }
        } catch (Exception e) {
            log.error("JWT 인증 에러: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    // 헤더에서 "Bearer "를 제외한 토큰 값만 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 Claims를 이용해 Authentication 객체 생성
    private Authentication getAuthentication(String token) {
        Claims claims = JwtUtils.extractClaims(token, secretKey);
        Long id = claims.get("id", Long.class);
        String username = claims.getSubject();
        String status = claims.get("status").toString();
        String role = claims.get("role").toString();

        AuthUserDto authUserDto = new AuthUserDto(
                id,
                username,
                "",
                role,
                status
        );

        CustomUserDetails principal = new CustomUserDetails(authUserDto);

        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }
}
