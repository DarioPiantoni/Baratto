package it.unibs.elaborato.categoria;

import java.io.Serializable;
/**Classe che rappresenta il concetto di Campo di una categoria , contenente le varie caratteristiche che lo carratterizzano
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class Campo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nome;
	private TipoDato tipo;
	private boolean obbligatorio;
	
	/**
	 * Metodo Costruttore di un campo
	 * @param nome nome del campo
	 * @param tipo tipo di dato da salvare nel campo
	 * @param obbligatorio attributo che indica se il campo è obbligatorio o meno (true se obbligatorio)
	 */
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
