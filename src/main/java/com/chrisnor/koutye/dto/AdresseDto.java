package com.chrisnor.koutye.dto;

import java.io.Serializable;

import com.chrisnor.koutye.model.Adresse;

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
    /*
    public AdresseDto(Adresse adresse) {
        this.numero = adresse.getNumero();
        this.rue= adresse.getRue();
        this.commune = adresse.getCommune();
        this.departement= adresse.getDepartement();
        this.pays = adresse.getPays();
    }
    */
}
