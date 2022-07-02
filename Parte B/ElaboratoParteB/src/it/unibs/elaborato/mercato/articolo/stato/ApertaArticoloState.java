package it.unibs.elaborato.mercato.articolo.stato;

import java.io.Serializable;

import it.unibs.elaborato.mercato.articolo.Articolo;

/**
 * Classe che rappresenta lo stato APERTA di un articolo
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class ApertaArticoloState implements ArticoloState, Serializable {
	private static final long serialVersionUID = 1L;
	private Articolo articolo;
	
	/**
	 * Metodo costruttore che associa l' articolo allo stato
	 * @param articolo oggetto a cui associare lo stato
	 */
	public ApertaArticoloState(Articolo articolo) {
		this.articolo = articolo;
	}
	/**
	 * metodo che mantiene lo stato in APERTA
	 */
	@Override
	public void aperta() {
		this.articolo.cambiaStato(new ApertaArticoloState(this.articolo));
	}
	/**
	 * metodo che cambia lo stato dell' articolo da APERTA a CHIUSA
	 */
	@Override
	public void chiusa() {
		this.articolo.cambiaStato(new ChiusaArticoloState(this.articolo));
	}
	/**
	 * metodo mantiene lo stato dell' articolo in APERTA
	 */
	@Override
	public void inScambio() {
		this.articolo.cambiaStato(new ApertaArticoloState(this.articolo));
	}
	/**
	 * metodo che cambia lo stato dell' articolo da APERTA a RITIRATA
	 */
	@Override
	public void ritirata() {
		this.articolo.cambiaStato(new RitirataArticoloState(this.articolo));
	}
	/**
	 * metodo che cambia lo stato dell' articolo da APERTA a ACCOPPIATA
	 */
	@Override
	public void accoppiata() {
		this.articolo.cambiaStato(new AccoppiataArticoloState(this.articolo));
	}
	/**
	 * metodo che cambia lo stato dell' articolo da APERTA a SELEZIONATA
	 */
	@Override
	public void selezionata() {
		this.articolo.cambiaStato(new SelezionataArticoloState(this.articolo));
	}
	
	@Override
	public String toString() {
		return ArticoloState.APERTA;
	}

}
