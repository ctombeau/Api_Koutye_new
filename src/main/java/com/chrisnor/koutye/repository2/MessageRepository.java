package com.chrisnor.koutye.repository2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.modelmysql.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long> {

}
