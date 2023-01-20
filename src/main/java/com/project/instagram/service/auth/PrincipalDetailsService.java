package com.project.instagram.service.auth;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserId(username).orElse(null);

        String jpql = "select u from User u join fetch u.userDetail where u.userId = :userId";

        User user = entityManager.createQuery(jpql, User.class).setParameter("userId", username).getSingleResult();

        if(user == null) {
            throw new UsernameNotFoundException("아이디가 올바르지않습니다.");
        }

        return new PrincipalDetails(user);
    }
}