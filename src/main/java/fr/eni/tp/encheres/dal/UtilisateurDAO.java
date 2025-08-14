package fr.eni.tp.encheres.dal;

import java.util.List;

import fr.eni.tp.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	Utilisateur readEmail(String email);
	
	Utilisateur readPseudo(String pseudo);
	
	List<Utilisateur> findAll();
	
	void create(Utilisateur utilisateur);

	void update(Utilisateur utilisateur);

}
