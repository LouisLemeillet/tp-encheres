package fr.eni.tp.encheres.bo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Enchere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime date;
	private int montant;
	private Utilisateur acquereur;
	private ArticleAVendre articleAVendre;
	
	public Enchere() {
		this.acquereur = new Utilisateur();
		this.articleAVendre = new ArticleAVendre();
	}
	
	
	public Enchere(LocalDateTime date, int montant, Utilisateur acquereur, ArticleAVendre articleAVendre) {
		super();
		this.date = date;
		this.montant = montant;
		this.acquereur = acquereur;
		this.articleAVendre = articleAVendre;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public int getMontant() {
		return montant;
	}


	public void setMontant(int montant) {
		this.montant = montant;
	}


	public Utilisateur getAcquereur() {
		return acquereur;
	}


	public void setAcquereur(Utilisateur acquereur) {
		this.acquereur = acquereur;
	}


	public ArticleAVendre getArticleAVendre() {
		return articleAVendre;
	}


	public void setArticleAVendre(ArticleAVendre articleAVendre) {
		this.articleAVendre = articleAVendre;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enchere other = (Enchere) obj;
		return articleAVendre == other.articleAVendre;
	}
	

}
