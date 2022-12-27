package com.project.instagram.service.alert;

import com.project.instagram.domain.alert.Alert;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlertServiceImplTest {

    @Autowired
    private AlertService alertService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 알림전송성공() {
        alertService.sendAlert();
    }

    @Test
    void 알림조회() {
        // given
        User user = userRepository.findById(1L).orElseThrow(() -> {
            throw new RuntimeException();
        });
        // when
        List<Alert> alertList = alertService.loadAlertList(user.getUserCode());

        // then
        assertThat(alertList.size()).isEqualTo(1);

    }
}