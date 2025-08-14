package fr.eni.tp.encheres.exceptions;


import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> clefsExternalisations;
	
	public List<String> getClefsExternalisations() {
		return clefsExternalisations;
	}

	public void setClefsExternalisations(List<String> clefsExternalisations) {
		this.clefsExternalisations = clefsExternalisations;
	}

	public BusinessException() {
		super();
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	//Permet d'ajouter une clef d'erreur - @param clef - @comportement initialise la liste si besoin
	public void add(String clef) {
		if(clefsExternalisations == null) {
			clefsExternalisations = new ArrayList<>();
		}
		clefsExternalisations.add(clef);
	}
	
	//@return permet de confirmer si des erreurs ont été chargées
	public boolean isValid() {
		return clefsExternalisations == null || clefsExternalisations.isEmpty();
	}

}
