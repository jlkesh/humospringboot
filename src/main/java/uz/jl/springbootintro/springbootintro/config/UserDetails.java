package uz.jl.springbootintro.springbootintro.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.jl.springbootintro.springbootintro.domains.AuthRole;
import uz.jl.springbootintro.springbootintro.domains.AuthUser;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record UserDetails(AuthUser authUser) implements org.springframework.security.core.userdetails.UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.requireNonNullElse(authUser.getRoles(), List.of());
    }

    @Override
    public String getPassword() {
        return authUser().getPassword();
    }

    @Override
    public String getUsername() {
        return authUser().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
