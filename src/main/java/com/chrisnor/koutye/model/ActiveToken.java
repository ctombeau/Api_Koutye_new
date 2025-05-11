package com.chrisnor.koutye.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "active_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveToken {
	@Id
    private String jti;

    private String username;

    @Column(name = "last_activity")
    private LocalDateTime lastActivity;
}
