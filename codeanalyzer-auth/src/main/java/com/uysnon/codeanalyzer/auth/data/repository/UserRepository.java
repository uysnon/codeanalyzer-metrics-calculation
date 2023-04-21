package com.uysnon.codeanalyzer.auth.data.repository;


import com.uysnon.codeanalyzer.auth.model.CodeAnalyzerUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserRepository implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new CodeAnalyzerUser();
    }
}
