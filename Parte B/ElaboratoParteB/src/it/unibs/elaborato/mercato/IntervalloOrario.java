package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.time.LocalTime;

/**Classe che rappresenta un intervallo orario 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class IntervalloOrario implements Serializable{
	private static final long serialVersionUID = 1L;
	private LocalTime oraInizio;
	private LocalTime oraFine;
	
	/**
	 * Metodo costruttore di un intervallo orario
	 * @param oraInizio orario di inzio intervallo
	 * @param oraFine orario di fine intervallo
	 */
	public IntervalloOrario(LocalTime oraInizio, LocalTime oraFine){
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
}
