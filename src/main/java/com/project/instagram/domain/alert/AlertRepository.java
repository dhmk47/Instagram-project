package com.project.instagram.domain.alert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    @Query(value = "select alert_code, alert_type_code, from_user_code, to_user_code, create_date, update_date from alert where to_user_code = ?1", nativeQuery = true)
    public List<Alert> findAlertListByUserCode(Long userCode);
}