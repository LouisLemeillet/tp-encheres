package fr.eni.tp.encheres.bll;

import java.util.List;

import fr.eni.tp.encheres.bo.Categorie;

public interface CategorieService {
	
	void createCategorie(Categorie categorie);
	
	void deleteCategorie(Categorie categorie);
	
	List<Categorie> listerCategories();

}
