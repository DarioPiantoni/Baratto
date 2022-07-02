package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.time.LocalDateTime;

import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.Offerta;

public class Scambio implements Serializable{
	private static final long serialVersionUID = 1L;
	private int idArticoloA;
	private int idArticoloB;
	private Token token;//token usato per le risposte (se A deve rispondere A, B deve rispondere B)
	private Appuntamento appuntamento;
	
	public Scambio(int idArticoloA, int idArticoloB) {
		super();
		this.idArticoloA = idArticoloA;
		this.idArticoloB = idArticoloB;
		this.appuntamento=null;
		setToken(Token.B);
	}

	public int getIdArticoloA() {
		return idArticoloA;
	}

	public int getIdArticoloB() {
		return idArticoloB;
	}

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
	

	public void risposta(ListaArticoli articoli, Appuntamento a,Token t) {
		setToken(t);//a chi risponde
		this.setAppuntamento(a);
		if(!articoli.getArticolo(idArticoloA).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO) && !articoli.getArticolo(idArticoloB).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO)) {
			articoli.getArticolo(idArticoloA).cambioStatoOfferta(Offerta.IN_SCAMBIO);
			articoli.getArticolo(idArticoloB).cambioStatoOfferta(Offerta.IN_SCAMBIO);
		}
	}
	
	public void risposta(ListaArticoli articoli) {
		articoli.getArticolo(idArticoloA).cambioStatoOfferta(Offerta.CHIUSA);
		articoli.getArticolo(idArticoloB).cambioStatoOfferta(Offerta.CHIUSA);
	}
	
	public LocalDateTime getDataScadenza(PuntoInteresse punto,ListaArticoli articoli) throws IllegalStateException{
		int scadenza=0;

		if(appuntamento==null)
			throw(new IllegalStateException("Nessuna scadenza rilevata"));
		if(punto.getPiazza().equals(appuntamento.getPiazza()))
			scadenza=punto.getScadenza();
		
			
		
		LocalDateTime dataInizioScambio=articoli.getArticolo(idArticoloA).ultimoStato();
		LocalDateTime dataScadenza=dataInizioScambio.plusDays(scadenza);
		
		return dataScadenza;
	}
	
	public String visualizza(ListaArticoli articoli) {
		StringBuffer testo=new StringBuffer();
		testo.append(articoli.getArticolo(idArticoloA).getNome()+" -> "+articoli.getArticolo(idArticoloB).getNome()+"\n");
		
		if(appuntamento!=null)
			testo.append(appuntamento.toString()+"\n");
		
		return testo.toString();
	}
	
	
	
}
