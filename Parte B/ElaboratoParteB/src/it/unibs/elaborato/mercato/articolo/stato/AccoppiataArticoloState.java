package it.unibs.elaborato.mercato.articolo.stato;

import java.io.Serializable;

import it.unibs.elaborato.mercato.articolo.Articolo;
/**
 * Classe che rappresenta lo stato ACCOPPIATO di un articolo
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class AccoppiataArticoloState implements ArticoloState, Serializable {
	private static final long serialVersionUID = 1L;
	private Articolo articolo;
	
	/**
	 * metodo costruttore 
	 * @param articolo articolo a cui associare lo stato 
	 */
	public AccoppiataArticoloState(Articolo articolo) {
		this.articolo = articolo;
	}

	/**
	 * metodo che cambia lo stato dell' articolo da ACCOPPIATA a APERTA
	 */
	@Override
	public void aperta() {
		this.articolo.cambiaStato(new ApertaArticoloState(this.articolo));
	}
	/**
	 * metodo che MANTIENE lo stato dell' articolo in stato ACCOPPATA in quanto non puo passare direttamente a CHIUSA
	 */
	@Override
	public void chiusa() {
		this.articolo.cambiaStato(new AccoppiataArticoloState(this.articolo));
	}
	/**
	 * metodo che cambia lo stato dell' articolo da ACCOPPATA a IN SCAMBIO
	 */
	@Override
	public void inScambio() {
		this.articolo.cambiaStato(new InScambioArticoloState(this.articolo));
	}
	/**
	 * metodo che MANTIENE lo stato dell' articolo in ACCOPPATA 
	 */
	@Override
	public void ritirata() {
		this.articolo.cambiaStato(new AccoppiataArticoloState(this.articolo));
	}
	
	/**
	 * metodo che MANTIENE lo stato dell' articolo in ACCOPPATA
	 */
	@Override
	public void accoppiata() {
		this.articolo.cambiaStato(new AccoppiataArticoloState(this.articolo));
	}
	
	/**
	 * metodo che MANTIENE lo stato dell' articolo in ACCOPPATA
	 */
	@Override
	public void selezionata() {
		this.articolo.cambiaStato(new AccoppiataArticoloState(this.articolo));
	}

	@Override
	public String toString() {
		return ArticoloState.ACCOPPIATA;
	}
}
