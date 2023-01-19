package com.project.instagram.service.auth;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.handler.exception.auth.AuthException;
import com.project.instagram.handler.exception.auth.AuthExceptionResult;
import com.project.instagram.web.dto.user.CreateUserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean signUpUser(CreateUserRequestDto createUserRequestDto) {
        User signUpUser = createUserRequestDto.toUserEntity(passwordEncoder.encode(createUserRequestDto.getUserPassword()));

        User checkedUser = userRepository.findByUserId(signUpUser.getUserId()).orElse(null);

        if(checkedUser != null) {
            throw new AuthException(AuthExceptionResult.ALREADY_HAS_USER_EXCEPTION);
        }

        UserDetail userDetail = new UserDetail();
        signUpUser.setUserDetail(userDetail);

        userDetailRepository.save(userDetail);
        userRepository.save(signUpUser);

        return true;
    }

    @Override
    public boolean loadFacebookLoginUser(Map<String, Object> oAuthResponseMap) {
        log.info("check: {}", oAuthResponseMap);

        try {
            oAuthResponseMap.get("user_id");
        } catch (Exception e) {
            throw new NullPointerException();
        }
        return true;
    }
}