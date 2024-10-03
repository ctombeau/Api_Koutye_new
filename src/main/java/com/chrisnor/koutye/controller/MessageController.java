package com.chrisnor.koutye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.modelmysql.Message;
import com.chrisnor.koutye.modelmysql.ReadReceiptRequest;
import com.chrisnor.koutye.repository2.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
public class MessageController {
	
	@Autowired
    MessageRepository messageRepository;

    @GetMapping(value = "/messages/{channelId}")
    public Page<Message> findMessages(Pageable pageable, @PathVariable("channelId") String channelId) {
        return messageRepository.findAllByChannel(channelId, pageable);
    }

    @PostMapping(value = "/messages")
    public void sendReadReceipt(@RequestBody ReadReceiptRequest request) {
        messageRepository.sendReadReceipt(request.getChannel(), request.getUsername());
    }
}
