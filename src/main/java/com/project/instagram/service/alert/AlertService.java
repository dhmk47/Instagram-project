package com.project.instagram.service.alert;

import com.project.instagram.domain.alert.Alert;

import java.util.List;

public interface AlertService {
    public void sendAlert();
    public List<Alert> loadAlertList(Long userCode);
}