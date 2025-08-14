package fr.eni.tp.encheres.bll;

import java.util.List;

import fr.eni.tp.encheres.bo.Adresse;
import fr.eni.tp.encheres.bo.ArticleAVendre;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;

public interface ArticleService {
	
	void deleteArticle(ArticleAVendre articleAVendre);
	
	List<ArticleAVendre> lstArticlesUtilisateur(Utilisateur utilisateur);
	
	List<ArticleAVendre> findAll();

	List<Categorie> findAllCategorie();
	
	List<Adresse> findAllRetrait(long noAdresseUtilisateur);

	void createArticle(ArticleAVendre article);

	Adresse findRetraitById(long noAdresse);

	Categorie findCategorieById(long noCategorie);

	List<ArticleAVendre> findAllVendus();


}
