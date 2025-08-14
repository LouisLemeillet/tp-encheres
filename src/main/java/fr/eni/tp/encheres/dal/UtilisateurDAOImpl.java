package fr.eni.tp.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.encheres.bo.Adresse;
import fr.eni.tp.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	private static final String FIND_BY_EMAIL = "SELECT pseudo, email, nom, prenom, administrateur FROM UTILISATEURS WHERE email = :email";
	private static final String FIND_BY_PSEUDO = "SELECT u.pseudo, u.email, u.nom, u.prenom, u.credit, u.telephone, u.administrateur, u.no_adresse AS utilisateur_no_adresse, a.rue, a.ville, a.no_adresse AS adresse_no_adresse FROM UTILISATEURS u "
			+ "JOIN ADRESSES a ON u.no_adresse = a.no_adresse WHERE u.pseudo = :pseudo";
	private static final String FIND_ALL = "SELECT pseudo, email, nom, prenom, administrateur FROM UTILISATEURS";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Utilisateur> findAll(){
		
		return jdbcTemplate.query(FIND_ALL, new UtilisateurRowMapper());
	}
	
	@Override
	public Utilisateur readPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
	}
	
	@Override
	public Utilisateur readEmail(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		
		return jdbcTemplate.queryForObject(FIND_BY_EMAIL, namedParameters, new UtilisateurRowMapper());
	}
	
	@Override
	public void create(Utilisateur utilisateur) {

		
		String sql = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse)"
				+ "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :mot_de_passe, :credit, :administrateur, :noAdresse)";
		//on change entre le insert et le values pour les conventions de nommage : colonnes d'un côté, et valeurs paramètres de l'autre
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
		namedParameters.addValue("credit", utilisateur.getCredit() + 10);
		namedParameters.addValue("administrateur", utilisateur.isAdmin());
		namedParameters.addValue("noAdresse", utilisateur.getAdresse().getId());
		
		
		
		jdbcTemplate.update(sql, namedParameters);
		
		
	}
	
	@Override
	public void update(Utilisateur utilisateur) {
	    if (utilisateur == null) {
	        System.out.println("L'objet utilisateur est null !");
	        return;
	    }
	    System.out.println("Utilisateur reçu pour update:");
	    System.out.println("Pseudo: " + utilisateur.getPseudo());
	    System.out.println("Nom: " + utilisateur.getNom());
	    System.out.println("Prénom: " + utilisateur.getPrenom());
	    System.out.println("Email: " + utilisateur.getEmail());
	    System.out.println("Téléphone: " + utilisateur.getTelephone());

	    if (utilisateur.getAdresse() == null) {
	        System.out.println("Adresse de l'utilisateur est null !");
	    } else {
	        System.out.println("Adresse de l'utilisateur:");
	        System.out.println("  no_adresse: " + utilisateur.getAdresse().getId());
	        System.out.println("  rue: " + utilisateur.getAdresse().getRue());
	        System.out.println("  ville: " + utilisateur.getAdresse().getVille());
	        System.out.println("  code postal: " + utilisateur.getAdresse().getCodePostal());
	    }

	    
	    String sqlUtilisateur = "UPDATE UTILISATEURS SET nom = :nom, prenom = :prenom, email = :email, telephone = :telephone WHERE pseudo = :pseudo";

	    MapSqlParameterSource paramsUtilisateur = new MapSqlParameterSource();
	    paramsUtilisateur.addValue("pseudo", utilisateur.getPseudo());
	    paramsUtilisateur.addValue("nom", utilisateur.getNom());
	    paramsUtilisateur.addValue("prenom", utilisateur.getPrenom());
	    paramsUtilisateur.addValue("email", utilisateur.getEmail());
	    paramsUtilisateur.addValue("telephone", utilisateur.getTelephone());

	    jdbcTemplate.update(sqlUtilisateur, paramsUtilisateur);

	    if (utilisateur.getAdresse() != null && utilisateur.getAdresse().getId() != 0) {
	        
	        String sqlAdresse = "UPDATE ADRESSES SET rue = :rue, ville = :ville, code_postal = :code_postal WHERE no_adresse = :no_adresse";

	        MapSqlParameterSource paramsAdresse = new MapSqlParameterSource();
	        paramsAdresse.addValue("rue", utilisateur.getAdresse().getRue());
	        paramsAdresse.addValue("ville", utilisateur.getAdresse().getVille());
	        paramsAdresse.addValue("code_postal", utilisateur.getAdresse().getCodePostal());
	        paramsAdresse.addValue("no_adresse", utilisateur.getAdresse().getId());

	        int rowsUpdated = jdbcTemplate.update(sqlAdresse, paramsAdresse);
	        System.out.println("Lignes ADRESSES mises à jour: " + rowsUpdated);
	    } else {
	        System.out.println("Impossible de mettre à jour l'adresse : id adresse null");
	    }
	}
	
	
	
	private class UtilisateurRowMapper implements RowMapper<Utilisateur>{
		
		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur u = new Utilisateur();
			u.setPseudo(rs.getString("pseudo"));
			u.setNom(rs.getString("nom"));
			u.setPrenom(rs.getString("prenom"));
			u.setEmail(rs.getString("email"));
			u.setAdmin(rs.getBoolean("administrateur"));
			u.setCredit(rs.getInt("credit"));
			u.setTelephone(rs.getString("telephone"));
			
			Adresse adresse = new Adresse();
			long idAdresse = rs.getLong("adresse_no_adresse");
			System.out.println("Adresse ID lu depuis ResultSet: "+idAdresse);
			adresse.setId(idAdresse);
			adresse.setRue(rs.getString("rue"));
			adresse.setVille(rs.getString("ville"));
			
			u.setAdresse(adresse);
			
			return u;
			
		}
	}
	

}
