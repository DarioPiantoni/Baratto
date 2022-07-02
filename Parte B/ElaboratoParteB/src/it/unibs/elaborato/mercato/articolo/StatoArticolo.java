package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;
import java.time.LocalDateTime;

import it.unibs.elaborato.mercato.articolo.stato.ArticoloState;
/**Classe che rappresenta lo stato di un articolo in un determinato istante di tempo 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class StatoArticolo implements Serializable{
	private static final long serialVersionUID = 1L;
	private LocalDateTime data; //data relativa allo stato
	private ArticoloState offerta; //stato dell'offerta
	
	/**
	 * Metodo costruttore 
	 * @param data 
	 * @param offerta
	 */
	public StatoArticolo(LocalDateTime data, ArticoloState offerta) {
		super();
		this.data = data;
		this.offerta = offerta;
	}

	public LocalDateTime getData() {
		return data;
	}

	public ArticoloState getOfferta() {
		return offerta;
	}
}
