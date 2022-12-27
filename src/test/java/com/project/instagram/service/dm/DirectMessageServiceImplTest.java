package com.project.instagram.service.dm;

import com.project.instagram.domain.dm.DirectMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DirectMessageServiceImplTest {

    @Autowired
    private DirectMessageService directMessageService;

    @Test
    void DM전송성공() {
        directMessageService.sendDirectMessage();
    }

    @Test
    void DM조회성공() {
        directMessageService.loadDirectMessage();
    }

    @Test
    void DM개수검증() {
        // given

        // when
        List<DirectMessage> directMessageList = directMessageService.loadDirectMessage();

        // then
        assertThat(directMessageList.size()).isEqualTo(4);
    }
}