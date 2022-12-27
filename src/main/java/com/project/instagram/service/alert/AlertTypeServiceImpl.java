package com.project.instagram.service.alert;

import com.project.instagram.domain.alert.AlertType;
import com.project.instagram.domain.alert.AlertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlertTypeServiceImpl implements AlertTypeService {

    private final AlertTypeRepository alertTypeRepository;

    @Transactional
    @Override
    public void createAlertType(AlertType alertType) {

        alertTypeRepository.save(alertType);
    }
}