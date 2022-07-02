package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.time.LocalTime;

public class IntervalloOrario implements Serializable{
	private static final long serialVersionUID = 1L;
	private LocalTime oraInizio;
	private LocalTime oraFine;
	
	public IntervalloOrario(LocalTime oraInizio, LocalTime oraFine) {
		super();
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	@Override
	public String toString() {
		return this.oraInizio.getHour()+"."+this.oraInizio.getMinute()+" - "+this.oraFine.getHour()+"."+this.oraFine.getMinute();
	}
	
	

}
