package fr.eni.tp.encheres.bll;

import java.util.List;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.AdresseDAO;
import fr.eni.tp.encheres.dal.ArticleAVendreDAO;
import fr.eni.tp.encheres.dal.CategorieDAO;
import fr.eni.tp.encheres.dal.EnchereDAO;
import fr.eni.tp.encheres.dal.UtilisateurDAO;
import fr.eni.tp.encheres.exceptions.BusinessCode;
import fr.eni.tp.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;
	private ArticleAVendreDAO articleAVendreDAO;
	private CategorieDAO categorieDAO;
	private EnchereDAO enchereDAO;
	
	

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO,
			ArticleAVendreDAO articleAVendreDAO, CategorieDAO categorieDAO, EnchereDAO enchereDAO) {
		super();
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
		this.articleAVendreDAO = articleAVendreDAO;
		this.categorieDAO = categorieDAO;
		this.enchereDAO = enchereDAO;
	}

	@Override
	public List<Utilisateur> consulterUtilisateurs() {

		List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
		
		return utilisateurs;
	}
	
	

	@Override
	public Utilisateur consulterUtilisateurParPseudo(String pseudo) {
	
		return utilisateurDAO.readPseudo(pseudo);
	}

	@Override
	public void deleteUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void createUtilisateur(Utilisateur utilisateur) {
		System.out.println("Création utilisateur service : "+utilisateur.getPseudo());
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerPseudo(utilisateur.getPseudo(), be);
		isValid &= validerNom(utilisateur.getNom(), be);
		isValid &= validerPrenom(utilisateur.getPrenom(), be);
		isValid &= validerEmail(utilisateur.getEmail(), be);
		isValid &= validerTelephone(utilisateur.getTelephone(), be);
		isValid &= validerAdresse(utilisateur.getAdresse().getRue(), be);
		isValid &= validerCodePostal(utilisateur.getAdresse().getCodePostal(), be);
		isValid &= validerMotDePasse(utilisateur.getMotDePasse(), be);
		isValid &= validerConfirmationMotDePasse(utilisateur.getMotDePasseConfirmation(), be);
		if(isValid) {
			utilisateur.setMotDePasse(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(utilisateur.getMotDePasse()));
			adresseDAO.create(utilisateur.getAdresse());
			utilisateurDAO.create(utilisateur);
			System.out.println("Utilisateur créé");
		}
		else {
			throw be;
		}
		System.out.println("id adresse = " + utilisateur.getAdresse());
			
	}
	
	@Override
	@Transactional
	public void modifierUtilisateur(Utilisateur utilisateurForm) {
		Utilisateur utilisateurBase = utilisateurDAO.readPseudo(utilisateurForm.getPseudo());
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerPseudo(utilisateurForm.getPseudo(), be);
		isValid &= validerNom(utilisateurForm.getNom(), be);
		isValid &= validerPrenom(utilisateurForm.getPrenom(), be);
		isValid &= validerEmail(utilisateurForm.getEmail(), be);
		isValid &= validerTelephone(utilisateurForm.getTelephone(), be);
		isValid &= validerAdresse(utilisateurForm.getAdresse().getRue(), be);
		isValid &= validerCodePostal(utilisateurForm.getAdresse().getCodePostal(), be);
		if(isValid) {
			utilisateurBase.setNom(utilisateurForm.getNom());
			utilisateurBase.setPrenom(utilisateurForm.getPrenom());
			utilisateurBase.setEmail(utilisateurForm.getEmail());
			utilisateurBase.setTelephone(utilisateurForm.getTelephone());
			
			utilisateurBase.getAdresse().setRue(utilisateurForm.getAdresse().getRue());
			utilisateurBase.getAdresse().setVille(utilisateurForm.getAdresse().getVille());
			utilisateurBase.getAdresse().setCodePostal(utilisateurForm.getAdresse().getCodePostal());
			utilisateurDAO.update(utilisateurBase);
		}
		else {
			throw be;
		}
	}
	
	private boolean validerPseudo(String pseudo, BusinessException be) {
		if(pseudo == null || pseudo.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_PSEUDO_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerNom(String nom, BusinessException be) {
		if(nom == null || nom.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_NOM_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerPrenom(String prenom, BusinessException be) {
		if(prenom == null || prenom.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_PRENOM_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerEmail(String email, BusinessException be) {
		if(email == null || email.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_EMAIL_BLANK);
			return false;
		}
		if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			be.add(BusinessCode.VALIDATION_USER_EMAIL_PATTERN);
			return false;
		}
		return true;
	}
	
	private boolean validerTelephone(String telephone, BusinessException be) {
		if(telephone != null && !telephone.isBlank() && !telephone.matches("^(?:\\+33|0)[1-9](?:\\d{2}){4}$")) {
			be.add(BusinessCode.VALIDATION_USER_PHONE_PATTERN);
			return false;
		}
		return true;
	}
	
	private boolean validerAdresse(String adresse, BusinessException be) {
		if(adresse == null || adresse.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_ADRESSE_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerCodePostal(String codePostal, BusinessException be) {
		if(codePostal != null && !codePostal.isBlank() && !codePostal.matches("^(\\d){5}$")) {
			be.add(BusinessCode.VALIDATION_USER_CODEPOSTAL_PATTERN);
			return false;
		}
		return true;
	}
	
	private boolean validerMotDePasse(String motDePasse, BusinessException be) {
		if(motDePasse == null || motDePasse.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_MDP_BLANK);
			return false;
		}
		if(!motDePasse.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}")) {
			be.add(BusinessCode.VALIDATION_USER_MDP_PATTERN);
			return false;
		}
		return true;
	}
	
	private boolean validerConfirmationMotDePasse(String confirmation, BusinessException be) {
		if(confirmation == null || confirmation.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_CONFIRMATION_BLANK);
			return false;
		}
		return true;
		
		//A DEMANDER COMMENT ON PEUT VERIFIER QUE CA COLLE AU CHAMP D'AU DESSUS
	}

	


	
	
	

}
