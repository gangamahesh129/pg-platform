package com.pgplatform.auth.security;

import com.pgplatform.auth.entity.AuthUser;
import com.pgplatform.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (!authUser.getActive()) {
            throw new UsernameNotFoundException("User is inactive: " + username);
        }

        return new User(
                authUser.getPhoneNumber(),
                authUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + authUser.getRole().name()))
        );
    }

    public UserDetails loadUserByUsernameAndTenant(String username, Long tenantId) {
        AuthUser authUser = authUserRepository.findByPhoneNumberAndTenantId(username, tenantId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username + " for tenant: " + tenantId));

        if (!authUser.getActive()) {
            throw new UsernameNotFoundException("User is inactive: " + username);
        }

        return new User(
                authUser.getPhoneNumber(),
                authUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + authUser.getRole().name()))
        );
    }
}
