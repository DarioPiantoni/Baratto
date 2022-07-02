package it.unibs.elaborato.mercato.articolo;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.TipoDato;
/**Classe che descrive un campo compilabile che memorizza il valore assunto dal campo
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class CampoCompilabile extends Campo{
	private static final long serialVersionUID = 1L;
	private Valore valoreCampo;

	/**
	 * Metodo costruttore
	 * @param nome nome del campo
	 * @param tipo tipo del campo
	 * @param obbligatorio il campo è obbligatorio o meno
	 * @param v stringa del valore che deve assumere il campo
	 * @throws NumberFormatException Eccezione che viene lanciata in caso di errore di formato (valore intero - decimale ) 
	 * @throws IllegalArgumentException Eccezione che viene lanciata se il valore di un campo boolean è errato
	 */
	public CampoCompilabile(String nome, TipoDato tipo, boolean obbligatorio,String v) throws NumberFormatException,IllegalArgumentException{
		super(nome, tipo, obbligatorio);
		setValore(tipo, v);
	}
	
	/**
	 * Metodo costruttore con parametro campo
	 * @param c oggetto campo che contiene le informazioni relative al valore che deve assumere 
	 * @param v stringa del valore che deve assumere il campo
	 * @throws NumberFormatException Eccezione che viene lanciata in caso di errore di formato (valore intero - decimale ) 
	 * @throws IllegalArgumentException Eccezione che viene lanciata se il valore di un campo boolean è errato
	 */
	public CampoCompilabile(Campo c,String v) throws NumberFormatException,IllegalArgumentException{
		super(c.getNome(),c.getTipo(),c.isObbligatorio());
		setValore(c.getTipo(), v);
	}
	
	/**
	 * Metodo per settare il valore di un campo compilabile 
	 * @param tipo tipo del campo 
	 * @param v stringa del valore che deve assumere il campo
	 * @throws NumberFormatException Eccezione che viene lanciata in caso di errore di formato (valore intero - decimale ) 
	 * @throws IllegalArgumentException Eccezione che viene lanciata se il valore di un campo boolean è errato
	 */
	private void setValore(TipoDato tipo, String v) throws NumberFormatException,IllegalArgumentException{
		if(v.isEmpty())
			valoreCampo=null;
		else {
			if(tipo.equals(TipoDato.INTERO))
				valoreCampo=new Valore<Integer>(Integer.parseInt(v));
			else if(tipo.equals(TipoDato.DECIMALE))
				valoreCampo=new Valore<Double>(Double.parseDouble(v));
			else if(tipo.equals(TipoDato.PAROLA))
				valoreCampo=new Valore<String>(v);
			else
			{
				if(v.equalsIgnoreCase("Sì"))
					valoreCampo=new Valore<Boolean>(true);
				else if(v.equalsIgnoreCase("No"))
					valoreCampo=new Valore<Boolean>(false);
				else
					throw new IllegalArgumentException("Errore, i valore accettati sono Sì oppure No");
			}
		}
	}
	
	public Valore getValoreCampo() {
		return valoreCampo;
	}
	
	public String getNomeCampo() {
		return super.getNome();
	}
}