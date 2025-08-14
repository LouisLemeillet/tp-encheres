package fr.eni.tp.encheres.controller.security;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.encheres.bll.UtilisateurService;
import fr.eni.tp.encheres.bo.Utilisateur;

@Controller
@SessionAttributes({ "utilisateurEnSession" })
public class LoginController {
	
	private UtilisateurService utilisateurService;
	

	public LoginController(UtilisateurService utilisateurService) {
		super();
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}
	
	// Cette méthode met par défaut un nouveau membre en session
	@ModelAttribute("utilisateurEnSession")
	public Utilisateur utilisateurEnSession() {
		System.out.println("Add Attribut Session");
		return new Utilisateur();
		}
	
	@GetMapping("/session")
	public String chargerUtilisateurEnSession(@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession,
			Principal principal) {
		
		String pseudo = principal.getName();
		System.out.println("Pseudo récupéré dans principal : [" + pseudo + "]");
		
		Utilisateur aCharger = utilisateurService.consulterUtilisateurParPseudo(pseudo);
		if (aCharger != null) {
			utilisateurEnSession.setPseudo(aCharger.getPseudo());
			utilisateurEnSession.setNom(aCharger.getNom());
			utilisateurEnSession.setPrenom(aCharger.getPrenom());
			utilisateurEnSession.setEmail(aCharger.getEmail());
			utilisateurEnSession.setAdmin(aCharger.isAdmin());
			utilisateurEnSession.setAdresse(aCharger.getAdresse());

		} else {
			utilisateurEnSession.setPseudo(null);
			utilisateurEnSession.setNom(null);
			utilisateurEnSession.setPrenom(null);
			utilisateurEnSession.setEmail(null);
			utilisateurEnSession.setAdmin(false);
			utilisateurEnSession.setAdresse(null);

		}
		System.out.println(utilisateurEnSession);
	

		return "redirect:/";
	}
	
	

}
