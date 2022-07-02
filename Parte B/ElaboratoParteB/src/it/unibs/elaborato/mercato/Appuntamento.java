package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.time.LocalTime;
/**Classe che rappresenta un Appuntamento concordato  
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 *  
 */

public class Appuntamento implements Serializable{
	private static final long serialVersionUID = 1L;
	private String piazza;//città
	private String luogo;//piazzali, zone della citta, parcheggi, ecc
	private Giorno giorno;
	private LocalTime orario;
	
	/**
	 * Metodo costruttore di un'istanza Appuntamento
	 * @param piazza città stabilità per lo scambio
	 * @param luogo zona della città 
	 * @param giorno giorno dell'incontro
	 * @param orario orario di incontro
	 */
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
}
