package it.unibs.elaborato.chainofresponsability;

import it.unibs.elaborato.utente.Configuratore;
import it.unibs.elaborato.utente.Utente;
/**
 * Classe gestore della catena Chain of responsability che si occupa di gestire il tipo utente configuratore
 * se la richiesta non riguarda la tipologia configuratore viene passata al prossimo elemento della catena 
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */

public class ConfiguratoreRenderer implements UtenteRenderer{
	private final UtenteRenderer next;
	
	/**
	 * metodo costruttore che sceglie il prossimo gestore della lista 
	 * @param next oggetto da gestire
	 */
	public ConfiguratoreRenderer(UtenteRenderer next) {
		super();
		this.next = next;
	}
	
	/**
	 * metodo che gestisce la richiesta 
	 * @param utente oggetto da gestire
	 */
	@Override
	public int render(Utente utente) {
		if(utente instanceof Configuratore)
			return 1;
		return next.render(utente);
	}

}
