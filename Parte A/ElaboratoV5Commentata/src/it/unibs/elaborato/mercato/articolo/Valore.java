package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;

/**Classe che esprime il valore di un campo (che puo essere di tipo generico) 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class Valore <T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private T valore; 

	/**
	 * Metodo costruttore 
	 * @param valore valore di tipo generico 
	 */
	public Valore(T valore) {
		super();
		this.valore = valore;
	}

	public T getValore() {
		return valore;
	}
	
}
