package com.project.instagram.service.dm;

import com.project.instagram.domain.dm.DirectMessage;

import java.util.List;

public interface DirectMessageService {
    public void sendDirectMessage();
    public List<DirectMessage> loadDirectMessage();
}