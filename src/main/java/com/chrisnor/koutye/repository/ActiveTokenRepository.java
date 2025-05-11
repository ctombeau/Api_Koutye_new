package com.chrisnor.koutye.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisnor.koutye.model.ActiveToken;

public interface ActiveTokenRepository extends JpaRepository<ActiveToken, String> {

}
