package com.chrisnor.koutye.modelmysql;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message implements Serializable{
	 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
    private long idSender;
    private long idReceiver;
    private String message;
    private LocalDateTime time;
}
