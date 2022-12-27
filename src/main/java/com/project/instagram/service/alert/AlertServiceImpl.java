package com.project.instagram.service.alert;

import com.project.instagram.domain.alert.Alert;
import com.project.instagram.domain.alert.AlertRepository;
import com.project.instagram.domain.alert.AlertType;
import com.project.instagram.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void sendAlert() {
        User toUser = entityManager.find(User.class, 1L);
        User fromUser = entityManager.find(User.class, 2L);
        AlertType alertType = entityManager.find(AlertType.class, 1L);

        Alert alert = Alert.builder()
                .build();

        alert.setToUser(toUser);
        alert.setFromUser(fromUser);
        alert.setAlertType(alertType);

        alertRepository.save(alert);
    }

    @Override
    public List<Alert> loadAlertList(Long userCode) {
        List<Alert> alertList = alertRepository.findAlertListByUserCode(userCode);

        log.info("alertList: {}", alertList);
        return alertList;
    }
}