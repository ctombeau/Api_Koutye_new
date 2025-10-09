package com.chrisnor.koutye.dto;

import java.io.Serializable;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.Ferme;
import com.chrisnor.koutye.model.ImageAppartement;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.model.VideoAppartement;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppartementDto implements Serializable{
	
	private Long appartementId;
	
	private String description;
	
	private double prix;
	
	private String devise;
	
	private String username;
	
	private AdresseDto adresse;
	
	//private List<ImageAppartement> imageAppartements;
	
	//private List<VideoAppartement> videoAppartements;
	
	//private Utilisateur utilisateur;
	
	/*
	 public AppartementDto(Appartement appartement) {
	        this.appartementId = appartement.getAppartementId();
	        this.description = appartement.getDescription();
	        this.prix = appartement.getPrix();
	        this.devise = appartement.getDevise();
	        
	        if (appartement.getUtilisateur() != null) {
	            this.username = appartement.getUtilisateur().getUsername();
	        }

	        if (appartement.getAdresse() != null) {
	            this.adresse = new AdresseDto(appartement.getAdresse());
	        }

	        
	        this.imageAppartements = appartement.getImageAppartements();
	        this.videoAppartements = appartement.getVideoAppartements();

	        this.utilisateur = appartement.getUtilisateur(); 
	        
	    }
	*/
}
