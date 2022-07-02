package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;

public class Valore <T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private T valore;

	public Valore(T valore) {
		super();
		this.valore = valore;
	}

	public T getValore() {
		return valore;
	}
	
}
