package kr.gilmok.common.dto;

public record AuthUserDto(
        Long id,
        String username,
        String role,
        String status
) {
}
