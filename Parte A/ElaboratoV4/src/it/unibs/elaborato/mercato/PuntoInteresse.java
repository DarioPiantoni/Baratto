package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.util.ArrayList;

public class PuntoInteresse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String piazza;//città 
	private ArrayList<String> luoghi;//piazzali, zona nord
	private ArrayList<Giorno> giorni;
	private ArrayList<IntervalloOrario> intervalliOrari;
	private int scadenza;
	
	public PuntoInteresse(String piazza, ArrayList<String> luoghi, ArrayList<Giorno> giorni, ArrayList<IntervalloOrario> intervalliOrari,int scadenza) {
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

	public ArrayList<String> getLuoghi() {
		return luoghi;
	}

	public ArrayList<Giorno> getGiorni() {
		return giorni;
	}

	public ArrayList<IntervalloOrario> getIntervalliOrari() {
		return intervalliOrari;
	}
	

	public int getScadenza() {
		return scadenza;
	}

	@Override
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

	@Override
	public String toString() {
		StringBuffer testo = new StringBuffer();
	    
	    testo.append("Piazza: " + this.piazza + "\n");
	    testo.append("Luoghi:\n");
	    for(String luogo: luoghi)
	      testo.append("\t-> " + luogo + "\n");
	    testo.append("Giorni:\n");
	    for(Giorno giorno: giorni)
	      testo.append("\t-> " + giorno.toString() + "\n");
	    testo.append("Intervalli: ");
	    for(IntervalloOrario intervallo: intervalliOrari)
	      testo.append("\t-> " + intervallo.toString() + "\n");
	    
	    testo.append("Scadenza: "+this.scadenza);
	    
	    return testo.toString();
		
	} 
	
	
	
}
