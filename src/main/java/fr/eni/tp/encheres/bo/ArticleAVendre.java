package fr.eni.tp.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

public class ArticleAVendre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nom;
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateDebutEncheres;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateFinEncheres;
	
	private int statut;
	private int prixInitial;
	private int prixVente;
	private Utilisateur vendeur;
	private Categorie categorie;
	private Adresse retrait;
	
	public ArticleAVendre() {
		this.vendeur = new Utilisateur();
		this.categorie = new Categorie();
		this.retrait = new Adresse();
		
	}
	
	
	public ArticleAVendre(long id, String nom, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int statut, int prixInitial, int prixVente, Utilisateur vendeur,
			Categorie categorie, Adresse retrait) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.statut = statut;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.vendeur = vendeur;
		this.categorie = categorie;
		this.retrait = retrait;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}


	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}


	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}


	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}


	public int getStatut() {
		return statut;
	}


	public void setStatut(int statut) {
		this.statut = statut;
	}


	public int getPrixInitial() {
		return prixInitial;
	}


	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}


	public int getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}


	public Utilisateur getVendeur() {
		return vendeur;
	}


	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}


	public Categorie getCategorie() {
		return categorie;
	}


	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	public Adresse getRetrait() {
		return retrait;
	}


	public void setRetrait(Adresse retrait) {
		this.retrait = retrait;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleAVendre other = (ArticleAVendre) obj;
		return id == other.id;
	}
	

}
