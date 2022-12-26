package com.project.instagram.domain.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query(value = "select follow_code as followCode, create_date as createDate, update_date as updateDate, from_user_code, to_user_code from follow where to_user_code = :to_user_code ;", nativeQuery = true)
    public List<Follow> findByToUserCode(@Param(value = "to_user_code") Long toUserCode);
}
