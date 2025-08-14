package fr.eni.tp.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.tp.encheres.bo.Adresse;
import fr.eni.tp.encheres.bo.ArticleAVendre;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.AdresseDAO;
import fr.eni.tp.encheres.dal.ArticleAVendreDAO;
import fr.eni.tp.encheres.dal.CategorieDAO;
import fr.eni.tp.encheres.exceptions.BusinessCode;
import fr.eni.tp.encheres.exceptions.BusinessException;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	private ArticleAVendreDAO articleAVendreDAO;
	private CategorieDAO categorieDAO;
	private AdresseDAO adresseDAO;
	
	

	public ArticleServiceImpl(ArticleAVendreDAO articleAVendreDAO, CategorieDAO categorieDAO, AdresseDAO adresseDAO) {
		super();
		this.articleAVendreDAO = articleAVendreDAO;
		this.categorieDAO = categorieDAO;
		this.adresseDAO = adresseDAO;
	}


	@Override
	public void deleteArticle(ArticleAVendre articleAVendre) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ArticleAVendre> lstArticlesUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleAVendre> findAll() {
		return articleAVendreDAO.findAll();
	}
	
	@Override
	public List<Categorie> findAllCategorie(){
		return categorieDAO.findAll();
	}
	
	@Override
	public List<Adresse> findAllRetrait(long noAdresseUtilisateur){
		return adresseDAO.findAllRetrait(noAdresseUtilisateur);
	}
	
	@Override
	public Adresse findRetraitById(long noAdresse) {
		return adresseDAO.findRetraitById(noAdresse);
	}
	
	@Override
	public Categorie findCategorieById(long noCategorie) {
		return categorieDAO.findCategorieById(noCategorie);
	}
	
	@Override
	@Transactional
	public void createArticle(ArticleAVendre article) {
		System.out.println("Création utilisateur service : "+article.getNom());
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerNom(article.getNom(), be);
		isValid &= validerDescription(article.getDescription(), be);
		isValid &= validerPrixInitial(article.getPrixInitial(), be);
		isValid &= validerCategorie(article.getCategorie().getLibelle(), be);
		isValid &= validerDateDebutEncheres(article.getDateDebutEncheres(), be);
		boolean validFin = false;
		if(isValid) {
			validFin = validerDateFinEncheres(article.getDateDebutEncheres(), article.getDateFinEncheres(), be);
			isValid = validFin;
		};
		isValid &= validerRetrait(article.getRetrait(), be);
		if(isValid) {
			articleAVendreDAO.create(article);
			System.out.println("Article créé");
		}
		else {
			throw be;
		}
			
	}
	
	private boolean validerNom(String nom, BusinessException be) {
		if(nom == null || nom.isBlank()) {
			be.add(BusinessCode.VALIDATION_ARTICLE_NOM_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerDescription(String description, BusinessException be) {
		if(description == null || description.isBlank()) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DESCRIPTION_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerPrixInitial(int prixInitial, BusinessException be) {
		if(prixInitial <= 0) {
			be.add(BusinessCode.VALIDATION_ARTICLE_PRIX_ZERO);
			return false;
		}
		return true;
	}
	
	private boolean validerCategorie(String categorie, BusinessException be) {
		if(categorie == null || categorie.isBlank()) {
			be.add(BusinessCode.VALIDATION_ARTICLE_CATEGORIE_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerDateDebutEncheres(LocalDate localDate, BusinessException be) {
		if(localDate.isBefore(LocalDate.now())) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DATEDEBUT_INVALID);
			return false;
		}
		return true;
	}
	
	private boolean validerDateFinEncheres(LocalDate dateDebut, LocalDate dateFin, BusinessException be) {

		if(dateFin.isBefore(dateDebut)) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DATEFIN_BEFORE_DEBUT);
			return false;
		}
		
		LocalDate dateLimite = dateDebut.plusDays(90);
		if(dateFin.isAfter(dateLimite)) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DATEFIN_INVALID);
			return false;
		}
		return true;
	}
	
	private boolean validerRetrait(Adresse retrait, BusinessException be) {
		if(retrait == null) {
			be.add(BusinessCode.VALIDATION_ARTICLE_ADRESSE_RETRAIT_NULL);
			return false;
		}
		return true;
	}


}
