package com.chrisnor.koutye.repository2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.modelmysql.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

}
