package com.project.instagram.service.alert;

import com.project.instagram.domain.alert.AlertType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlertTypeServiceImplTest {

    @Autowired
    private AlertTypeService alertTypeService;

    @Test
    void 알림종류생성() {
        // given
        AlertType alertType = AlertType.builder()
                .alertContent("좋아요를 눌렀습니다.")
                .build();

        // when
        alertTypeService.createAlertType(alertType);
        //then


    }
}