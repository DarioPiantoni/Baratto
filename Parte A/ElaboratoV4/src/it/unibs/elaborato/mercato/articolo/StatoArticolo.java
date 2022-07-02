package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StatoArticolo implements Serializable{
	private static final long serialVersionUID = 1L;
	private LocalDateTime data;
	private Offerta offerta;
	
	public StatoArticolo(LocalDateTime data, Offerta offerta) {
		super();
		this.data = data;
		this.offerta = offerta;
	}

	public LocalDateTime getData() {
		return data;
	}

	public Offerta getOfferta() {
		return offerta;
	}
}
