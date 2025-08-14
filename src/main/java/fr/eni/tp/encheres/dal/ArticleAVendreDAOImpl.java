package fr.eni.tp.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.tp.encheres.bo.Adresse;
import fr.eni.tp.encheres.bo.ArticleAVendre;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {
	

	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String FIND_BY_UTILISATEUR = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait FROM ARTICLES_A_VENDRE"
										+"WHERE id_utilisateur = :idUtilisateur";
	
	private static final String FIND_ALL_ACTIVE = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait FROM ARTICLES_A_VENDRE"
			+ " WHERE statut_enchere = 1";

	@Override
	public void create(ArticleAVendre articleAVendre, String pseudo) {
		
		
		
		
		String sql = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait)" 
				+ "VALUES (:nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :statutEnchere, :prixInitial, :prixVente, :idUtilisateur, :noCategorie, :noAdresseRetrait)";
	
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("nomArticle", articleAVendre.getNom());
		namedParameters.addValue("description", articleAVendre.getDescription());
		namedParameters.addValue("dateDebutEncheres", articleAVendre.getDateDebutEncheres());
		namedParameters.addValue("dateFinEncheres", articleAVendre.getDateFinEncheres());
		namedParameters.addValue("statutEnchere", articleAVendre.getStatut());
		namedParameters.addValue("prixInitial", articleAVendre.getPrixInitial());
		namedParameters.addValue("prix de vente", articleAVendre.getPrixVente());
		namedParameters.addValue("idUtilisateur", pseudo);
		namedParameters.addValue("noCategorie", articleAVendre.getCategorie());
		namedParameters.addValue("noAdresseRetrait", articleAVendre.getRetrait());
		
		jdbcTemplate.update(sql, namedParameters);
		
		
		

	}

	@Override
	public List<ArticleAVendre> findByUtilisateur(String pseudo) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idUtilisateur", pseudo);
		
		return jdbcTemplate.query(FIND_BY_UTILISATEUR, namedParameters, new ArticleRowMapper());
	}
	
	@Override
	public List<ArticleAVendre> findAll(){
		return jdbcTemplate.query(FIND_ALL_ACTIVE, new ArticleRowMapper());
	}
	
	@Override
	public void create(ArticleAVendre article) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String sql = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, photo, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait)"
				+ "VALUES (:nomArticle, :description, :photo, :date_debut_encheres, :date_fin_encheres, :statut_enchere, :prix_initial, :prix_vente, :id_utilisateur, :no_categorie, :no_adresse_retrait)";
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("nomArticle", article.getNom());
		namedParameters.addValue("description", article.getDescription());
		namedParameters.addValue("photo", null);
		namedParameters.addValue("date_debut_encheres", article.getDateDebutEncheres());
		namedParameters.addValue("date_fin_encheres", article.getDateFinEncheres());
		namedParameters.addValue("statut_enchere", 0);
		namedParameters.addValue("prix_initial", article.getPrixInitial());
		namedParameters.addValue("prix_vente", null);
		namedParameters.addValue("id_utilisateur", article.getVendeur().getPseudo());
		namedParameters.addValue("no_categorie", article.getCategorie().getId());
		namedParameters.addValue("no_adresse_retrait", article.getRetrait().getId());
		
		
		
		jdbcTemplate.update(sql, namedParameters, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			article.setId(keyHolder.getKey().longValue());
		}
		
		
	}

	
	
	private class ArticleRowMapper implements RowMapper<ArticleAVendre> {

		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre a = new ArticleAVendre();
			a.setId(rs.getLong("no_article"));
			a.setNom(rs.getString("nom_article"));
			a.setDescription(rs.getString("description"));
			a.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
			a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
			a.setStatut(rs.getInt("statut_enchere"));
			a.setPrixInitial(rs.getInt("prix_initial"));
			a.setPrixVente(rs.getInt("prix_vente"));
			
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setPseudo(rs.getString("id_utilisateur"));
			a.setVendeur(utilisateur);
			
			Categorie categorie = new Categorie();
			categorie.setId(rs.getLong("no_categorie"));
			a.setCategorie(categorie);
			
			Adresse retrait = new Adresse();
			retrait.setId(rs.getLong("no_adresse_retrait"));
			a.setRetrait(retrait);
			
			return a;
		}
		
	}

}
