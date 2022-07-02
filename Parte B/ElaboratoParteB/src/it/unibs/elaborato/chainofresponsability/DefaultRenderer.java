package it.unibs.elaborato.chainofresponsability;

import it.unibs.elaborato.utente.Utente;

/**
 * Classe default di gestione della catena Chain of responsability che si occupa di gestire i casi limite, nei casi in cui nessun oggetto della catena è riuscito a gestire la richiesta 
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class DefaultRenderer implements UtenteRenderer{

	/**
	 * metodo che gestisce la richiesta
	 * @param utente oggetto da gestire
	 * @return ritorna 0 per comunicare al client che non è stato possibile gestire la richiesta
	 */
	@Override
	public int render(Utente utente) {
		return 0;
	}
	
}