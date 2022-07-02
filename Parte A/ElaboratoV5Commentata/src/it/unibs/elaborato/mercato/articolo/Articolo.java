package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.utente.Fruitore;

/**Classe che rappresena il conceto di Articolo
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 *   
 */
public class Articolo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Fruitore proprietario; // fruitore proprietario dell'articolo
	private String nome; 
	private Categoria categoria;
	private ArrayList<CampoCompilabile> campi; // valori dei campi compilabili
	private ArrayList<StatoArticolo> storicoOfferte; //lista delle offerte passate e in corso 
	
	/**
	 * Metodo costruttore di un oggetto Articolo
	 * @param proprietario
	 * @param nome
	 * @param categoria
	 * @param campi
	 */
	public Articolo(Fruitore proprietario,String nome, Categoria categoria, ArrayList<CampoCompilabile> campi) {
		super();
		this.proprietario=proprietario;
		this.nome = nome;
		this.categoria = categoria;
		this.campi = campi;
		this.storicoOfferte=new ArrayList<StatoArticolo>();
	}
	
	/**
	 * Metodo che permette di aggiungere un'offerta allo storico 
	 * @param o offerta da aggiungere allo storico offerte
	 * 
	 * @precondition o!=null
	 * @postcondition storicoOfferte.size()<storicoOfferte'.size()
	 */
	public void cambioStatoOfferta(Offerta o) {
		this.storicoOfferte.add(new StatoArticolo(LocalDateTime.now(),o));
	}
	
	/**
	 * Metodo che seleziona dallo storico l'offerta attualmente in corso 
	 * @return ritorna un oggetto Offerta 
	 * 
	 * @precondition storicoOfferte!=null
	 * @postcondition storicoOfferte.size()==storicoOfferte'.size()
	 */
	public Offerta statoOffertaAttuale() {
		return this.storicoOfferte.get(this.storicoOfferte.size()-1).getOfferta();
	}
	
	/**
	 * Metodo che seleziona la data dell'ultima offerta 
	 * @return ritorna un oggetto LocalDateTime contenente la data dell'ultima offerta
	 * 
	 * @precondition storicoOfferte!=null
	 * @postcondition storicoOfferte.size()==storicoOfferte'.size()
	 */
	public LocalDateTime ultimoStato() {
		return this.storicoOfferte.get(this.storicoOfferte.size()-1).getData();
	}

	public Fruitore getProprietario() {
		return proprietario;
	}

	public String getNome() {
		return nome;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public ArrayList<CampoCompilabile> getCampi() {
		return campi;
	}
	
	@Override
	public String toString() {
		StringBuffer testo=new StringBuffer();
		testo.append("Nome: "+this.nome+"\n");
		testo.append("Categoria: "+this.categoria.getNome()+"\n");
		testo.append("Campi\n");
		if(!this.campi.isEmpty()) {
			for(CampoCompilabile c:this.campi)
				testo.append(c.toString()+"\n");
		}else
			testo.append("Non sono presenti campi compilabili\n");
		
		testo.append("Stato: "+this.statoOffertaAttuale()+"\n");
		return testo.toString();
	}
	
	
}
