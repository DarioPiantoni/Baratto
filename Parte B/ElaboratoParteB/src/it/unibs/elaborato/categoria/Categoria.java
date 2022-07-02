package it.unibs.elaborato.categoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**Classe che rappresenta il concetto di Categoria, la quale è organizzata in uno schema ad albero 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nome;
	private String descrizione;
	private List<Campo> campi;
	private Categoria padre;
	
	/**
	 * Metodo costruttore di una categoria 
	 * @param nome nome della categoria 
	 * @param descrizione stringa che descrive le caratteristiche della categoria 
	 * @param campi array di oggetti Campo che la categoria deve avere 
	 * @param padre categoria padre , null se categoria radice
	 */
	public Categoria(String nome, String descrizione, List<Campo> campi,Categoria padre) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.campi = campi;
		this.padre=padre;
		
		//se si tratta di una categoria radice metto i campi di nativi
		if(this.padre==null && this.campi!=null) {
			this.campi.add(0,new Campo("Stato di conservazione",TipoDato.PAROLA,true));
			this.campi.add(1,new Campo("Descrizione libera",TipoDato.PAROLA,false));
		}
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public List<Campo> getCampi() {
		return campi;
	}

	public Categoria getPadre() {
		return padre;
	}

	public void setPadre(Categoria padre) {
		this.padre = padre;
	}

	@Override
	/**
	 * metodo equals che ritorna true se l'oggetto passato come paramentro è una categoria e ha lo stesso nome della categoria su cui è stato invocato il metodo
	 * @param obj oggetto da controllare 
	 * @return true: se l'oggetto è uguale false: se non lo è 
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
