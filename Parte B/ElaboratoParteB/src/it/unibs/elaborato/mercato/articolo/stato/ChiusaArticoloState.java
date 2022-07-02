package it.unibs.elaborato.mercato.articolo.stato;

import java.io.Serializable;

import it.unibs.elaborato.mercato.articolo.Articolo;

/**
 * Classe che rappresenta lo stato CHIUSA di un articolo (che deriva l' interfaccia di stato)
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class ChiusaArticoloState implements ArticoloState, Serializable {
	private static final long serialVersionUID = 1L;
	private Articolo articolo;

	/**
	 * Metodo costruttore che associa l' articolo allo stato
	 * @param articolo oggetto a cui associare lo stato
	 */
	public ChiusaArticoloState(Articolo articolo) {
		this.articolo = articolo;
	}

	/**
	 * metodo che mantiene lo stato in CHIUSA, non puo passare subito ad APERTA
	 */
	@Override
	public void aperta() {
		this.articolo.cambiaStato(new ChiusaArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in CHIUSA
	 */
	@Override
	public void chiusa() {
		this.articolo.cambiaStato(new ChiusaArticoloState(this.articolo));
	}
	
	/**
	 * metodo che mantiene lo stato in CHIUSA ( non puo passare in stato IN SCAMBIO)
	 */
	@Override
	public void inScambio() {
		this.articolo.cambiaStato(new ChiusaArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in CHIUSA ( non puo passare in stato RITIRATA)
	 */
	@Override
	public void ritirata() {
		this.articolo.cambiaStato(new ChiusaArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in CHIUSA ( non puo passare in stato ACCOPPIATA)
	 */
	@Override
	public void accoppiata() {
		this.articolo.cambiaStato(new ChiusaArticoloState(this.articolo));
	}
	/**
	 * metodo che mantiene lo stato in CHIUSA ( non puo passare in stato SELEZIONATA)
	 */
	@Override
	public void selezionata() {
		this.articolo.cambiaStato(new ChiusaArticoloState(this.articolo));
	}

	@Override
	public String toString() {
		return ArticoloState.CHIUSA;
	}
	
}
