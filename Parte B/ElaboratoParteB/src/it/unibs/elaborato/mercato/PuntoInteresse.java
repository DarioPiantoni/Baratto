package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
/**Classe che rappresenta un Punto di interesse (possibile punto di ritrovo per uno scambio) 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class PuntoInteresse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String piazza;//città 
	private List<String> luoghi;//piazzali, zona nord, ecc
	private List<Giorno> giorni;
	private List<IntervalloOrario> intervalliOrari;
	private int scadenza;
	
	/**
	 * Metodo costruttore di un Punto di interesse
	 * @param piazza 
	 * @param luoghi
	 * @param giorni
	 * @param intervalliOrari
	 * @param scadenza
	 */
	public PuntoInteresse(String piazza, List<String> luoghi, List<Giorno> giorni, List<IntervalloOrario> intervalliOrari,int scadenza) {
		super();
		this.piazza = piazza;
		this.luoghi = luoghi;
		this.giorni = giorni;
		this.intervalliOrari = intervalliOrari;
		this.scadenza=scadenza;
	}

	public String getPiazza() {
		return piazza;
	}

	public List<String> getLuoghi() {
		return luoghi;
	}

	public List<Giorno> getGiorni() {
		return giorni;
	}

	public List<IntervalloOrario> getIntervalliOrari() {
		return intervalliOrari;
	}
	

	public int getScadenza() {
		return scadenza;
	}

	@Override
	/**
	 * Metodo equals (2 punti di interesse sono uguali se hanno la stessa "piazza")
	 * @param obj oggetto sul quale effetturare il confronto
	 * @return ritorna TRUE se l oggetto passato è un punto di interesse uguale a quello su cui è stato invocato il metodo
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuntoInteresse other = (PuntoInteresse) obj;
		if (piazza == null) {
			if (other.piazza != null)
				return false;
		} else if (!piazza.equals(other.piazza))
			return false;
		return true;
	}
}
