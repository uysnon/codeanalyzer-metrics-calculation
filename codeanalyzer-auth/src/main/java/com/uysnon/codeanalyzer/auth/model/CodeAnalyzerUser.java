package com.uysnon.codeanalyzer.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.EnumSet;

@Data
@NoArgsConstructor
public class CodeAnalyzerUser implements UserDetails {
    private String username;
    private String password;
    private String email;
    private EnumSet<Roles> roles;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().sorted().map(r -> (GrantedAuthority) r::getAuthority).toList();
    }
}
