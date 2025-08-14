package fr.eni.tp.encheres.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.encheres.bll.ArticleService;
import fr.eni.tp.encheres.bo.Adresse;
import fr.eni.tp.encheres.bo.ArticleAVendre;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@SessionAttributes({"utilisateurEnSession", "categorieEnSession", "retraitEnSession"})
public class ArticleController {
	
	private final ArticleService articleService;
	
	
	public ArticleController(ArticleService articleService) {
		super();
		this.articleService = articleService;
	}
	
	@ModelAttribute("article")
	public ArticleAVendre initArticle() {
		ArticleAVendre article = new ArticleAVendre();
		return article;
	}
	
	@ModelAttribute("categorieEnSession")
	public List<Categorie> chargerCategorie() {
		System.out.println("Chargement en Session - CATEGORIES");
		return articleService.findAllCategorie();
	}
	
	@ModelAttribute("retraitEnSession")
	public List<Adresse> chargerAdresseRetrait(@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {
		System.out.println("Chargement en Session - ADRESSES RETRAIT");
		long noAdresseUtilisateur = utilisateurEnSession.getAdresse().getId();
		return articleService.findAllRetrait(noAdresseUtilisateur);
	}
	
	@GetMapping("/")
	public String redirectionAccueil() {
	    return "redirect:/index";
	}

	@GetMapping("/index")
	public String afficherAccueil(@RequestParam(name = "categorieId", required = false) Long categorieId,
								  @RequestParam(name="ventesUtilisateur", required=false) Integer statutEnchere,
								  @ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession,
	                              Model model) {
		List<ArticleAVendre> listeArticleAVendre = articleService.findAll();
		
		
		if (categorieId != null && statutEnchere != null && statutEnchere == 1) {
			listeArticleAVendre = listeArticleAVendre.stream()
					.filter(article -> article.getStatut() == statutEnchere.intValue())
	                .filter(article -> Long.valueOf(article.getCategorie().getId()).equals(categorieId))
	                .collect(Collectors.toList());
	    }
		
		if(utilisateurEnSession != null) {
			
			if(statutEnchere != null && (statutEnchere == 1 || statutEnchere == 2)) {
				listeArticleAVendre.addAll(articleService.findAllVendus());
				listeArticleAVendre = listeArticleAVendre.stream()
						.filter(article -> article.getStatut() == statutEnchere.intValue())
						.filter(article -> article.getVendeur().getPseudo().equals(utilisateurEnSession.getPseudo()))
						.collect(Collectors.toList());
			} 
	

		}
			
		model.addAttribute("listeArticleAVendre", listeArticleAVendre);
	    model.addAttribute("categorieEnSession", articleService.findAllCategorie());
	    model.addAttribute("categorieIdSelectionnee", categorieId);
	    return "index";
	}
	
	@PostMapping("/index")
	public String afficherAccueilPostFiltre(@RequestParam(name="articleNom", required=false) String articleNom, Model model) {
		List<ArticleAVendre> listeArticleAVendre = articleService.findAll();
		
		if(articleNom != null) {
			listeArticleAVendre = listeArticleAVendre.stream()
					.filter(article -> article.getNom().toLowerCase().contains(articleNom))
					.collect(Collectors.toList());
		}
		
		model.addAttribute("listeArticleAVendre", listeArticleAVendre);
		model.addAttribute("categorieEnSession", articleService.findAllCategorie());
		return "index";
	}
	
	@GetMapping("/vendre")
	public String vendreArticle(Model model, @ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {
		
		
		if (utilisateurEnSession != null) {
			
			long noAdresseUtilisateur = utilisateurEnSession.getAdresse().getId();
			model.addAttribute("categorieEnSession", articleService.findAllCategorie());
			model.addAttribute("retraitEnSession", articleService.findAllRetrait(noAdresseUtilisateur));
			model.addAttribute("article", new ArticleAVendre());
			
			return "view-article-form";
		} else {
			
			return "redirect:/login";
		}
	} 
	
	
	@PostMapping("/vendre")
	public String creerArticleSansPhoto(@Valid @ModelAttribute("article") ArticleAVendre articleAVendre, BindingResult bindingResult, @SessionAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {
		
		articleAVendre.setVendeur(utilisateurEnSession);
		
		if(!bindingResult.hasErrors()) {
			try {
				
				articleService.createArticle(articleAVendre);
				return "redirect:/";
			}
			catch(BusinessException e) {
				e.getClefsExternalisations().forEach(key -> {
					ObjectError error = new ObjectError("globalError", key);
					bindingResult.addError(error);
				//String[] champ = key.split("\\.");
				//bindingResult.rejectValue(champ[2], key);
				});
			}
		}
		return "view-article-form";
	}
	
	@GetMapping("/vendre/photo")
	public String vendreArticlePhoto(Model model, @RequestParam("idArticle") Long idArticle, @ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {
		
		
		if(utilisateurEnSession == null) {
			return "redirect:/index";
		}
		if(idArticle == null) {
			return "redirect:/vendre";
		}
		model.addAttribute("idArticle", idArticle);
		return "view-article-form-photo";
	}
	
	

}
