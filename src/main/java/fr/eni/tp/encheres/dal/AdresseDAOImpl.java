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
import fr.eni.tp.encheres.bo.Utilisateur;

@Repository
public class AdresseDAOImpl implements AdresseDAO {
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String FIND_BY_RETRAIT = "SELECT rue, code_postal, ville, adresse_eni FROM ADRESSES WHERE no_adresse = :idAdresseRetrait";
	private static final String FIND_BY_UTILISATEUR = "SELECT rue, code_postal, ville, adresse_eni FROM ADRESSES WHERE no_adresse = :idAdresseUtilisateur";
	private static final String INSERT_ADRESSE = "INSERT INTO ADRESSES (rue, code_postal, ville) VALUES (:rue, :code_postal, :ville)";
	private static final String FIND_ALL_RETRAIT = "SELECT rue, code_postal, ville, no_adresse FROM ADRESSES WHERE adresse_eni = 1 OR no_adresse = :noAdresseUtilisateur"
			+ " ORDER BY CASE WHEN no_adresse = :noAdresseUtilisateur THEN 0 ELSE 1 END";
	private static final String FIND_RETRAIT_BY_ID = "SELECT rue, code_postal, ville, no_adresse FROM ADRESSES WHERE no_adresse = :noAdresse";
	
	@Override
	public Adresse findByArticle(long idAdresseRetrait) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idAdresseRetrait", idAdresseRetrait);
		
		return (Adresse) jdbcTemplate.query(FIND_BY_RETRAIT, namedParameters, new AdresseRowMapper());
	}

	@Override
	public Adresse findByUtilisateur(long idAdresseUtilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idAdresseUtilisateur", idAdresseUtilisateur);
		
		return (Adresse) jdbcTemplate.query(FIND_BY_UTILISATEUR, namedParameters, new AdresseRowMapper());
	}
	
	@Override
	public void create(Adresse adresse) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("rue", adresse.getRue());
		namedParameters.addValue("code_postal", adresse.getCodePostal());
		namedParameters.addValue("ville", adresse.getVille());
		
		jdbcTemplate.update(INSERT_ADRESSE, namedParameters, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			adresse.setId(keyHolder.getKey().longValue());
		}
		
	}
	
	@Override
	public List<Adresse> findAllRetrait(long noAdresseUtilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("noAdresseUtilisateur", noAdresseUtilisateur);
		
		return jdbcTemplate.query(FIND_ALL_RETRAIT, namedParameters, new AdresseRowMapper());
	}
	
	@Override
	public Adresse findRetraitById(long noAdresse) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("noAdresse", noAdresse);
		
		return jdbcTemplate.queryForObject(FIND_RETRAIT_BY_ID, namedParameters, new AdresseRowMapper());
	}
	
	private class AdresseRowMapper implements RowMapper<Adresse> {

		@Override
		public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
			Adresse a = new Adresse();
			a.setId(rs.getLong("no_adresse"));
			a.setRue(rs.getString("rue"));
			a.setCodePostal(rs.getString("code_postal"));
			a.setVille(rs.getString("ville"));
	
			return a;
		}
		
	}
		

}
