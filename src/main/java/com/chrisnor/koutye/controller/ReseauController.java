package com.chrisnor.koutye.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.reseau.MyClient;

import jakarta.websocket.server.ServerEndpoint;

//@ServerEndpoint("/api/reseau")
@RequestMapping("/api/reseau")
@RestController
public class ReseauController {
	@PostMapping("/send")
    public void SendMessage() throws IOException{
    	new MyClient().sendMessage("ctombeau","jedma","Ou la?");
    }
}
