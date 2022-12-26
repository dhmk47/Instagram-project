package com.project.instagram.domain.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query(value = "select follow_code, create_date, update_date, from_user_code, to_user_code from follow where to_user_code = ?1", nativeQuery = true)
    public List<Follow> findByToUserCode(Long toUserCode);
}
