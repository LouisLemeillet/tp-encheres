package fr.eni.tp.encheres.bll;

import java.util.List;

import fr.eni.tp.encheres.bo.Utilisateur;

public interface UtilisateurService {
	
	List<Utilisateur> consulterUtilisateurs();
	
	Utilisateur consulterUtilisateurParPseudo(String pseudo);
	
	void deleteUtilisateur(Utilisateur utilisateur);
	
	void createUtilisateur(Utilisateur utilisateur);


	void modifierUtilisateur(Utilisateur utilisateur);
	
	

}
