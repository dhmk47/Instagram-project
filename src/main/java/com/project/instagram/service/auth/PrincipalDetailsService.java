package com.project.instagram.service.auth;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserId(username).orElse(null);

        String jpql = "select u from User u join fetch u.userDetail where u.userId = :userId";

        List<User> userList = entityManager.createQuery(jpql, User.class).setParameter("userId", username).getResultList();

        if(userList.isEmpty()) {
            throw new UsernameNotFoundException("아이디가 올바르지않습니다.");

        }

        return new PrincipalDetails(userList.get(0));
    }
}