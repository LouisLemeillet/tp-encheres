package fr.eni.tp.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.tp.encheres.bll.ArticleService;
import fr.eni.tp.encheres.bo.Categorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie>{
	
	private ArticleService articleService;

	public StringToCategorieConverter(ArticleService articleService) {
		super();
		this.articleService = articleService;
	}
	
	@Override
	public Categorie convert(String noCategorie) {
		if(noCategorie == null || noCategorie.isEmpty()) {
			return null;
		}
		
		return articleService.findCategorieById(Long.parseLong(noCategorie));
	}
	

}
