package it.unibs.elaborato.mercato;

import java.io.Serializable;

import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.stato.ArticoloState;

/**Classe che rapprensenta un possibile scambio tra due articoli 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 * 
 * @invariant token!=null
 */
public class Scambio implements Commerciabile,Serializable{
	private static final long serialVersionUID = 1L;
	private int idArticoloA;
	private int idArticoloB;
	private Token token;//token usato per le risposte (se A deve rispondere A, B deve rispondere B)
	private Appuntamento appuntamento;
	
	/**
	 * Metodo costruttore di uno Scambio
	 * @param idArticoloA articolo 1 da scambiare
	 * @param idArticoloB articolo 2 da scambiare
	 */
	public Scambio(int idArticoloA, int idArticoloB) {
		super();
		this.idArticoloA = idArticoloA;
		this.idArticoloB = idArticoloB;
		this.appuntamento=null; // l' appuntamento è null fino a quando i due fruitori non si accordano
		setToken(Token.B); // inizialmente il token è settato su B in quanto dovra rispondere il fruitore proprietario dell'articolo B
	}

	public int getIdArticoloA() {
		return idArticoloA;
	}

	public int getIdArticoloB() {
		return idArticoloB;
	}

	/*
	 * Metodo Set che permette di fissare l appuntamento per lo scambio
	 * @param appuntamento appuntamento da impostare
	 */
	public void setAppuntamento(Appuntamento appuntamento) {
		this.appuntamento = appuntamento;
	}

	public Appuntamento getAppuntamento() {
		return appuntamento;
	}
	
	private void setToken(Token token) {
		this.token = token;
	}

	public Token getToken() {
		return token;
	}
	

	/**
	 * Metodo che in seguito a una proposta di scambio imposta gli stati delle offerte relative agli articoli in scambio
	 * @param articoli lista degli articoli presenti nel sistema
	 * @param a appuntamento concordato tra i fruitori 
	 * @param t token contenente quale fruitore deve rispondere 
	 * 
	 * @precondition t!=null && articoli!=null && a!=null
	 * @postcondition !t.equals(t') && !a.equals(a') 
	 * 						&& articoli.getArticolo(idArticoloA).statoOffertaAttuale() == Offerta.IN_SCAMBIO
	 *						&& articoli.getArticolo(idArticoloB).statoOffertaAttuale() == Offerta.IN_SCAMBIO
	 */
	public void risposta(ListaArticoli articoli, Appuntamento a,Token t) {
		setToken(t);//a chi risponde
		this.setAppuntamento(a);
		if(!articoli.getArticolo(idArticoloA).statoOffertaAttuale().toString().equals(ArticoloState.IN_SCAMBIO) && !articoli.getArticolo(idArticoloB).statoOffertaAttuale().toString().equals(ArticoloState.IN_SCAMBIO)) {
			articoli.getArticolo(idArticoloA).inScambio();
			articoli.getArticolo(idArticoloB).inScambio();
		}
	}
	
	/**
	 * Metodo che modifica lo stato delle offerte degli articoli in scambio in chiuse  
	 * @param articoli lista degli articoli presenti nel sistema
	 * 
	 * @precondition articoli != null
	 * @postcondition articoli.getArticolo(idArticoloA).statoOffertaAttuale() == Offerta.CHIUSA
	 *				  && articoli.getArticolo(idArticoloB).statoOffertaAttuale() == Offerta.CHIUSA
	 */
	public void risposta(ListaArticoli articoli) {
		articoli.getArticolo(idArticoloA).chiusa();
		articoli.getArticolo(idArticoloB).chiusa();
	}
}
