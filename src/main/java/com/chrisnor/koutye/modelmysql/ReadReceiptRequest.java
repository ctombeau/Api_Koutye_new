package com.chrisnor.koutye.modelmysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadReceiptRequest {
	 private String channel;
	 private String username;
}
