package kr.gilmok.common.security;

import kr.gilmok.common.dto.AuthUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(AuthUserDto user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = (user.role() == null || user.role().isBlank()) ? "ROLE_USER" : user.role();
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return user.username();
    }

    @Override
    public String getPassword() {
        return user.passwordHash();
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVE".equals(user.status());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"LOCKED".equals(user.status());
    }
}