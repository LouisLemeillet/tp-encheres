package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Enchere;

public interface EnchereDAO {

	void create(Enchere enchere);
	
	//Enchere findLastEnchere(LocalDateTime date); -pour trouver la dernière enchère en date sur un article
	
	void delete(String idUtilisateur, long noArticle);
	
}
