package it.unibs.elaborato.mercato.articolo.stato;

import java.io.Serializable;

import it.unibs.elaborato.mercato.articolo.Articolo;
/**
 * Classe che rappresenta lo stato SELEZIONATA di un articolo (deriva l' interfaccia di stato)
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class SelezionataArticoloState implements ArticoloState, Serializable {
	private static final long serialVersionUID = 1L;
	private Articolo articolo;
	/**
	 * Metodo costruttore che associa l' articolo allo stato
	 * @param articolo oggetto a cui associare lo stato
	 */
	public SelezionataArticoloState(Articolo articolo) {
		this.articolo = articolo;
	}
	/**
	 * metodo che cambia lo stato dell' articolo da SELEZIONATA ad APERTA 
	 */
	@Override
	public void aperta() {
		this.articolo.cambiaStato(new ApertaArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in SELEZIONATA, non puo passare direttamente a CHIUSA
	 */
	@Override
	public void chiusa() {
		this.articolo.cambiaStato(new SelezionataArticoloState(this.articolo));
	}
	
	/**
	 * metodo che cambia lo stato dell' articolo da SELEZIONATA ad IN SCAMBIO 
	 */
	@Override
	public void inScambio() {
		this.articolo.cambiaStato(new InScambioArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in SELEZIONATA, non puo passare direttamente a RITIRATA
	 */
	@Override
	public void ritirata() {
		this.articolo.cambiaStato(new SelezionataArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in SELEZIONATA, non puo passare direttamente ad ACCOPPIATA
	 */
	@Override
	public void accoppiata() {
		this.articolo.cambiaStato(new SelezionataArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in SELEZIONATA
	 */
	@Override
	public void selezionata() {
		this.articolo.cambiaStato(new SelezionataArticoloState(this.articolo));
	}

	@Override
	public String toString() {
		return ArticoloState.SELEZIONATA;
	}

}
