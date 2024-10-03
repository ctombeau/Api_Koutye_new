package com.chrisnor.koutye.repository2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.modelmysql.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long> {
	Page<Message> findAllByChannel(String channel, Pageable pageable);

    @Modifying
    @Query(value = "update message set readDate = now() "
            + " where channel = ?1 and sender = ?2 and readDate is null", nativeQuery = true)
    void sendReadReceipt(String channel, String username);
}
