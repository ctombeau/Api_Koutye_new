package com.chrisnor.koutye.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdresseDto implements Serializable{
	private int numero;
    private String rue;
    private String commune;
    private String departement;
    private String pays;
}
