package fr.eni.tp.encheres.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
	
	@ModelAttribute("dateDuJour")
	public String dateDuJour() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.FRENCH);
		return LocalDate.now().format(formatter);
	}

}
