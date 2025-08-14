package fr.eni.tp.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Utilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String motDePasse;
	private String motDePasseConfirmation;
	private int credit;
	private boolean admin;
	private Adresse adresse;
	private List<ArticleAVendre> articlesAVendre;
	
	public Utilisateur() {
		this.adresse = new Adresse();
		this.articlesAVendre = new ArrayList<ArticleAVendre>();
	}
	
	
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String motDePasse,
			int credit, boolean admin, Adresse adresse, List<ArticleAVendre> articlesAVendre) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.admin = admin;
		this.adresse = adresse;
		this.articlesAVendre = articlesAVendre;
	}


	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getMotDePasse() {
		return motDePasse;
	}


	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getMotDePasseConfirmation() {
		return motDePasseConfirmation;
	}


	public void setMotDePasseConfirmation(String motdePasseConfirmation) {
		this.motDePasseConfirmation = motdePasseConfirmation;
	}


	public int getCredit() {
		return credit;
	}


	public void setCredit(int credit) {
		this.credit = credit;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}


	public Adresse getAdresse() {
		return adresse;
	}


	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	
	public List<ArticleAVendre> getArticlesAVendre() {
		return articlesAVendre;
	}


	public void setArticlesAVendre(List<ArticleAVendre> articlesAVendre) {
		this.articlesAVendre = articlesAVendre;
	}


	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return email == other.email;
	}
	
	
	

}
