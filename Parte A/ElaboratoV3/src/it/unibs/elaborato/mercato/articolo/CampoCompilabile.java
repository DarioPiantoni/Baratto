package it.unibs.elaborato.mercato.articolo;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.TipoDato;

public class CampoCompilabile extends Campo{
	private static final long serialVersionUID = 1L;
	private Valore valoreCampo;
	
	/**Metodo costruttore
	 * @param nome nome del campo
	 * @param tipo tipo del campo
	 * @param obbligatorio il campo è obbligatorio o meno
	 * @param v stringa del valore che deve assumere il campo
	 */
	public CampoCompilabile(String nome, TipoDato tipo, boolean obbligatorio,String v) {
		super(nome, tipo, obbligatorio);
		setValore(tipo, v);
	}
	
	/**Metodo costruttore con parametro campo
	 * @param c campo
	 * @param v stringa del valore che deve assumere il campo
	 */
	public CampoCompilabile(Campo c,String v) {
		super(c.getNome(),c.getTipo(),c.isObbligatorio());
		setValore(c.getTipo(), v);
	}


	/**Metodo per settare il valore di un campo compilabile
	 * @param tipo tipo del campo
	 * @param v stringa del valore che deve assumere il campo
	 */
	private void setValore(TipoDato tipo, String v) {
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
				else
					valoreCampo=new Valore<Boolean>(false);
			}
		}
	}

	@Override
	public String toString() {
		if(valoreCampo==null)
			return super.getNome()+" : Nessun valore inserito";
		else
			return super.getNome()+" : "+this.valoreCampo.getValore();
	}
}