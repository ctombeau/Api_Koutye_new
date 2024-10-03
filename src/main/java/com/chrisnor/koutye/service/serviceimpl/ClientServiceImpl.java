package com.chrisnor.koutye.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.exception.UsernameAlreadyUsedException;
import com.chrisnor.koutye.modelmysql.Client;
import com.chrisnor.koutye.repository2.ClientRepository;
import com.chrisnor.koutye.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
    private ClientRepository clientDao;
	
	@Override
	public Client connect(Client user) throws UsernameAlreadyUsedException {
		Client dbClient = clientDao.findByUsername(user.getUsername());

        if (dbClient != null) {

            if (dbClient.getConnected()) {
                throw new UsernameAlreadyUsedException("This user is already connected: " + dbClient.getUsername());
            }

            dbClient.setConnected(true);
            return clientDao.save(dbClient);
        }

        user.setConnected(true);
        return clientDao.save(user);
	}

	@Override
	public Client disconnect(Client client) {
		if (client == null) {
            return null;
        }

        Client dbClient = clientDao.findByUsername(client.getUsername());
        if (dbClient == null) {
            return client;
        }

        dbClient.setConnected(false);
        return clientDao.save(dbClient);
	}

}
