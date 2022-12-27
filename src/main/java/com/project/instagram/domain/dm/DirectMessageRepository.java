package com.project.instagram.domain.dm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    @Query(value = "select direct_message_code, to_user_code, from_user_code, direct_message, create_date, update_date from direct_message where to_user_code = ?1", nativeQuery = true)
    public List<DirectMessage> findDirectMessageByToUserCode(Long userCode);
}