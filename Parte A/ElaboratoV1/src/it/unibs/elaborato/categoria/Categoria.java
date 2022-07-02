package it.unibs.elaborato.categoria;

import java.io.Serializable;
import java.util.ArrayList;

public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nome;
	private String descrizione;
	private ArrayList<Campo> campi;
	private Categoria padre;
	
	public Categoria(String nome, String descrizione, ArrayList<Campo> campi,Categoria padre) {
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

	public Categoria getPadre() {
		return padre;
	}

	@Override
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

	@Override
	public String toString() {
		StringBuilder testo=new StringBuilder();
		testo.append(this.nome+":"+this.descrizione);
		testo.append(this.campi.toString());
		if(this.padre==null)
			testo.append(",Padre: Radice");
		else
			testo.append(",Padre:"+this.padre.nome);
		
		return testo.toString();
		
	}
}