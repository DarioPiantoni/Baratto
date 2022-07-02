package it.unibs.elaborato.categoria;

import java.io.Serializable;

public class Campo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nome;
	private TipoDato tipo;
	private boolean obbligatorio;
	
	public Campo(String nome, TipoDato tipo, boolean obbligatorio) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.obbligatorio = obbligatorio;
	}

	public String getNome() {
		return nome;
	}
	

	public TipoDato getTipo() {
		return tipo;
	}

	public boolean isObbligatorio() {
		return obbligatorio;
	}

	@Override
	public String toString() {
		StringBuilder testo=new StringBuilder();
		testo.append(this.nome+" : "+this.tipo.toString()+", Obbligatorio:");
		if(obbligatorio)
			testo.append("Sì");
		else
			testo.append("No");
		return testo.toString();
	}
	
	
	
}
