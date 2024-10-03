package com.chrisnor.koutye.repository2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chrisnor.koutye.modelmysql.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
	    Client findByUsername(String username);

	    @Transactional
	    Long deleteByUsername(String username);
}
