package com.chrisnor.koutye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.modelmysql.Message;
import com.chrisnor.koutye.repository2.MessageRepository;
import java.util.Date;

@RestController
public class ChatController {
	 @Autowired
	 private SimpMessagingTemplate template;

	  @Autowired
	  private MessageRepository messageRepository;
	  
	  	@MessageMapping("/messages")
	    public void handleMessage(Message message) {
	        message.setTimestamp(new Date());
	        messageRepository.save(message);
	        template.convertAndSend("/channel/chat/" + message.getChannel(), message);
	    }
}
