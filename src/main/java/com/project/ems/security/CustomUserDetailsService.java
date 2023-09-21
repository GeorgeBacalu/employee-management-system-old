package com.project.ems.security;

import com.project.ems.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.USERNAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new SecurityUser(userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username))));
    }
}
