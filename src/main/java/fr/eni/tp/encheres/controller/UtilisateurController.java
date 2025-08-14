package fr.eni.tp.encheres.controller;

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

import fr.eni.tp.encheres.bll.UtilisateurService;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@SessionAttributes({"utilisateurEnSession"})
public class UtilisateurController {
	
private UtilisateurService utilisateurService;
	
	public UtilisateurController(UtilisateurService utilisateurService) {	
		this.utilisateurService = utilisateurService;
		
	}
	
	@ModelAttribute("profil")
	public Utilisateur initProfil() {
		Utilisateur profil = new Utilisateur();
		return profil;
	}
	
	@GetMapping("/profil/creer")
	public String creerProfil(Model model, @ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession, @ModelAttribute("profil") Utilisateur profil) {
		if (utilisateurEnSession != null) {
			
			return "view-profil-form";
		} else {
			
			return "redirect:/login";
		}
	}
	
	@PostMapping("/profil/creer")
	public String creerProfil(@Valid @ModelAttribute("profil") Utilisateur profil, BindingResult bindingResult) {
		System.out.println("Appel du controleur creer");
		if(!bindingResult.hasErrors()) {
			try {
				
				utilisateurService.createUtilisateur(profil);
				return "redirect:/profil";
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
		return "view-profil-form";
	}
	
	
	
	@GetMapping("/profil")
	public String consulterProfil(Model model, @RequestParam(value="pseudo", required=false) String pseudo, @SessionAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {

		if(pseudo == null) {
			pseudo = utilisateurEnSession.getPseudo();
		}
		
		
		Utilisateur profil = utilisateurService.consulterUtilisateurParPseudo(pseudo);
		boolean consulter = true;
		
		model.addAttribute("profil", profil);
		if (profil == null) {
			
			return "redirect:/login";
		} 
		
		if(profil.getPseudo().equals(utilisateurEnSession.getPseudo())) {
			consulter = false;
		} 
	
		model.addAttribute("consulter", consulter);

		return "view-profil";
	}
	
	@GetMapping("/profil/modifier")
	public String modifierProfil(Model model, @ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {
		Utilisateur profil = utilisateurService.consulterUtilisateurParPseudo(utilisateurEnSession.getPseudo());
		model.addAttribute("profil", profil);
		if (profil != null) {
			
			return "view-profil-modifier";
		} else {
			
			return "redirect:/login";
		}
	}
	
	@PostMapping("/profil/modifier")
	public String modifierProfil(@Valid @ModelAttribute("profil") Utilisateur profil, BindingResult bindingResult) {
		System.out.println("Appel du controleur creer");
		if(!bindingResult.hasErrors()) {
			try {
				
				utilisateurService.modifierUtilisateur(profil);
				System.out.println("Appel du controleur modifier");
				return "redirect:/profil";
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
		return "view-profil-modifier";
	}
	
	
	

}
