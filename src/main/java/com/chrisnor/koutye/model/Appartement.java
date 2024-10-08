package com.chrisnor.koutye.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@DynamicInsert
public class Appartement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="appartement_id")
	private Long AppartementId;
	
	@NotNull
	private String description;
	
	@Column(nullable = false, columnDefinition="default 0.0")
	private double prix;
	
	private String devise;
	
	@JsonBackReference
	//@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="utilisateur_id")
	private Utilisateur utilisateur;
	
	
	@JsonManagedReference
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="adresse_id")
	private Adresse adresse;
	
	@JsonManagedReference
	@OneToMany(mappedBy="appartement")
	private List<Ferme> ferme;
	
	@JsonManagedReference
	@OneToMany(mappedBy="appartement", fetch=FetchType.EAGER,cascade = CascadeType.PERSIST)
	private List<ImageAppartement> imageAppartements;
	
	@JsonManagedReference
	@OneToMany(mappedBy="appartement", fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	private List<VideoAppartement> videoAppartements;
}
