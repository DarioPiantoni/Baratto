package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.time.LocalTime;

public class Appuntamento implements Serializable{
	private static final long serialVersionUID = 1L;
	private String piazza;//città
	private String luogo;//piazzali, zona nord
	private Giorno giorno;
	private LocalTime orario;
	
	public Appuntamento(String piazza, String luogo, Giorno giorno, LocalTime orario) {
		super();
		this.piazza = piazza;
		this.luogo = luogo;
		this.giorno = giorno;
		this.orario = orario;
	}

	public String getPiazza() {
		return piazza;
	}

	public String getLuogo() {
		return luogo;
	}

	public Giorno getGiorno() {
		return giorno;
	}

	public LocalTime getOrario() {
		return orario;
	}

	@Override
	public String toString() {
		StringBuffer testo=new StringBuffer("Appuntamento\n");
		testo.append("Piazza: "+this.piazza+"\n");
		testo.append("Luogo: "+this.luogo+"\n");
		testo.append("Giorno: "+this.giorno+"\n");
		testo.append("Orario: "+this.orario.toString()+"\n");
		
		return testo.toString();
	}
}
