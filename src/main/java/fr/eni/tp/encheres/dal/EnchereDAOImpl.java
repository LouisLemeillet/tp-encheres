package fr.eni.tp.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.encheres.bo.Enchere;


@Repository
public class EnchereDAOImpl implements EnchereDAO {
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Enchere enchere) {
		
		String sql = "INSERT INTO ENCHERES (id_utilisateur, no_article, montant_enchere, date_enchere)" 
					+ "VALUES (:idUtilisateur, :noArticle, :montantEnchere, :dateEnchere)";
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idUtilisateur", enchere.getAcquereur());
		namedParameters.addValue("noArticle", enchere.getArticleAVendre());
		namedParameters.addValue("montantEnchere", enchere.getMontant());
		namedParameters.addValue("dateEnchere", enchere.getDate());

		
		jdbcTemplate.update(sql, namedParameters);
		
		
	}
	

	@Override
	public void delete(String idUtilisateur, long noArticle) {

		String sql = "DELETE FROM ENCHERES WHERE id_utilisateur = :idUtilisateur AND no_article = noArticle";
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idUtilisateur", idUtilisateur);
		namedParameters.addValue("noArticle", noArticle);
		
		
		jdbcTemplate.update(sql, namedParameters);
	}

}
