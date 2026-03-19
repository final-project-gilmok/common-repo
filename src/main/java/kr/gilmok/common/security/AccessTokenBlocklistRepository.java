package kr.gilmok.common.security;

public interface AccessTokenBlocklistRepository {

    /**
     * access token의 jti를 블랙리스트에 등록한다 (auth-repo 전용).
     * @param jti 토큰의 JWT ID
     * @param ttlMs 블랙리스트 보존 기간 (토큰 남은 유효시간, 밀리초)
     */
    default void block(String jti, long ttlMs) {
        throw new UnsupportedOperationException("이 모듈에서는 블랙리스트 등록 기능이 지원되지 않습니다.");
    }

    /**
     * jti가 블랙리스트에 존재하는지 확인한다.
     * @return 블랙리스트에 있으면 true (= 로그아웃된 토큰)
     */
    boolean isBlocked(String jti);
}
