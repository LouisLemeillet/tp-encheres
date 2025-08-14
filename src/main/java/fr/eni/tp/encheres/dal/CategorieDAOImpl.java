package fr.eni.tp.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.encheres.bo.Categorie;


@Repository
public class CategorieDAOImpl implements CategorieDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String FIND_BY_ID = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = :id";
	private static final String FIND_ALL = "SELECT no_categorie, libelle FROM CATEGORIES";
	private static final String FIND_CATEGORIE_BY_ID = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = :noCategorie";

	@Override
	public Categorie read(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, namedParameters, new CategorieRowMapper());
	}

	@Override
	public List<Categorie> findAll() {
		
		return jdbcTemplate.query(FIND_ALL, new CategorieRowMapper());
	}
	
	@Override
	public Categorie findCategorieById(long noCategorie) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("noCategorie", noCategorie);
		
		return jdbcTemplate.queryForObject(FIND_CATEGORIE_BY_ID, namedParameters, new CategorieRowMapper());
		
	}
	
	private class CategorieRowMapper implements RowMapper<Categorie> {

		@Override
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Categorie c = new Categorie();
			c.setId(rs.getLong("no_categorie"));
			c.setLibelle(rs.getString("libelle"));
			
			return c;
		}
		
	}
	

}
