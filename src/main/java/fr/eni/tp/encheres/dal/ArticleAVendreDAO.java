package fr.eni.tp.encheres.dal;

import java.util.List;

import fr.eni.tp.encheres.bo.ArticleAVendre;

public interface ArticleAVendreDAO {
	
	void create(ArticleAVendre articleAVendre, String pseudo);
	
	List<ArticleAVendre> findByUtilisateur(String pseudo);
	
	List<ArticleAVendre> findAll();

	void create(ArticleAVendre article);

	List<ArticleAVendre> findAllVendus();

}
