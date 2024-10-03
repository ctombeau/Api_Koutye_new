package com.chrisnor.koutye.service;

import com.chrisnor.koutye.exception.UsernameAlreadyUsedException;
import com.chrisnor.koutye.modelmysql.Client;

public interface ClientService {
	 Client connect(Client user) throws UsernameAlreadyUsedException;
	 Client disconnect(Client user);
}
