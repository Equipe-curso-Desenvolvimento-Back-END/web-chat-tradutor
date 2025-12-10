package com.chat.chat_tradutor.service;

import com.chat.chat_tradutor.repository.MessageRepository;
import com.chat.chat_tradutor.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

}
