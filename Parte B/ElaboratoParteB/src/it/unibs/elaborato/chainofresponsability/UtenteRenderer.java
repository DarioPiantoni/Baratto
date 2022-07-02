package it.unibs.elaborato.chainofresponsability;

import it.unibs.elaborato.utente.Utente;
/**
 * interfaccia Handler che descrive i gestori di una chain of responsability
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public interface UtenteRenderer{
	
	/**
	 * definizione del metodo per la gestione delle richieste
	 * @param utente oggetto da gestire
	 * @return ritorna un intero indicante il tipo di utente ( 1-> CONFIGURATORE , 2-> FRUITORE , 0-> TIPO NON DICHIARATO) 
	 */
	int render(Utente utente);
}
