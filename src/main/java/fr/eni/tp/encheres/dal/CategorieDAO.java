package fr.eni.tp.encheres.dal;

import java.util.List;

import fr.eni.tp.encheres.bo.Categorie;

public interface CategorieDAO {
	
	Categorie read(long id);
	
	List<Categorie> findAll();

	Categorie findCategorieById(long noCategorie);

}
