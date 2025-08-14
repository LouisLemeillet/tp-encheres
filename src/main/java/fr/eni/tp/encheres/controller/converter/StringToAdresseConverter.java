package fr.eni.tp.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.tp.encheres.bll.ArticleService;
import fr.eni.tp.encheres.bo.Adresse;

@Component
public class StringToAdresseConverter implements Converter<String, Adresse> {
	
private ArticleService articleService;
	
	public StringToAdresseConverter(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Adresse convert(String noAdresse) {
		if(noAdresse == null || noAdresse.isEmpty()) {
			return null;
		}
		
		return articleService.findRetraitById(Long.parseLong(noAdresse));
	}
	
}
