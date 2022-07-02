package it.unibs.elaborato.chainofresponsability;

import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.Utente;

/**
 * Classe gestore della catena Chain of responsability che si occupa di gestire il tipo utente FRUITORE
 * se la richiesta non riguarda la tipologia FRUITORE viene passata al prossimo elemento della catena 
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */

public class FruitoreRenderer implements UtenteRenderer{
	private final UtenteRenderer next;
	
	/**
	 * metodo costruttore che sceglie il prossimo gestore della lista 
	 * @param next oggetto da gestire
	 */
	public FruitoreRenderer(UtenteRenderer next) {
		super();
		this.next = next;
	}
	
	/**
	 * metodo che gestisce la richiesta 
	 * @param utente oggetto da gestire
	 */
	public int render(Utente utente) {
		if(utente instanceof Fruitore) {
			return 2;
		}
		return next.render(utente);
	}
	

}
