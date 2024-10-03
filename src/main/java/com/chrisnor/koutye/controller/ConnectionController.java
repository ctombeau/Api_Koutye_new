package com.chrisnor.koutye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.modelmysql.Client;
import com.chrisnor.koutye.repository2.ClientRepository;
import com.chrisnor.koutye.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.chrisnor.koutye.exception.UsernameAlreadyUsedException;

@RestController
public class ConnectionController {
	@Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientDao;

    @Autowired
    private SimpMessagingTemplate template;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody Client client) {

        try {
            Client connectedClient = clientService.connect(client);
            template.convertAndSend("/channel/login", connectedClient);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestBody Client client) {
        Client disconnectedUser = clientService.disconnect(client);
        template.convertAndSend("/channel/logout", disconnectedUser);
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Client> findConnectedUsers() {
        return clientDao.findAll();
    }

    @RequestMapping(value = "/clearAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearAll() {
        clientDao.deleteAll();
    }

}
