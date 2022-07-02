package it.unibs.elaborato.mercato;

import it.unibs.elaborato.mercato.articolo.ListaArticoli;

/**
 * Interfaccia utile per implementare pattern Low Coupling , in modo da aumentare il riuso e per facilitare future espansioni 
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public interface Commerciabile {
	public void risposta(ListaArticoli articoli, Appuntamento a,Token t);
	public void risposta(ListaArticoli articoli);
}