package com.project.instagram.service.auth;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElse(null);

        if(user == null) {
            throw new UsernameNotFoundException("아이디가 올바르지않습니다.");
        }

        return new PrincipalDetails(user);
    }
}