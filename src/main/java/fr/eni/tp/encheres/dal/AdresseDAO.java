package fr.eni.tp.encheres.dal;

import java.util.List;

import fr.eni.tp.encheres.bo.Adresse;

public interface AdresseDAO {
	
	Adresse findByArticle(long idAdresseRetrait);
	
	Adresse findByUtilisateur(long idUtilisateur);

	void create(Adresse adresse);

	List<Adresse> findAllRetrait(long noAdresseUtilisateur);

	Adresse findRetraitById(long noAdresse);

}
