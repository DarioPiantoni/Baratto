package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.mercato.articolo.stato.ApertaArticoloState;
import it.unibs.elaborato.mercato.articolo.stato.ArticoloState;
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
	private Collection<CampoCompilabile> campi; // valori dei campi compilabili
	private List<StatoArticolo> storicoOfferte; //lista delle offerte passate e in corso 
	private ArticoloState state;
	
	/**
	 * Metodo costruttore di un oggetto Articolo
	 * @param proprietario
	 * @param nome
	 * @param categoria
	 * @param campi
	 */
	public Articolo(Fruitore proprietario,String nome, Categoria categoria, Collection<CampoCompilabile> campi) {
		super();
		this.proprietario=proprietario;
		this.nome = nome;
		this.categoria = categoria;
		this.campi = campi;
		this.storicoOfferte=new ArrayList<StatoArticolo>();
		cambiaStato(new ApertaArticoloState(this));
	}
	
	/**
	 * Metodo che seleziona dallo storico l'offerta attualmente in corso 
	 * @return ritorna un oggetto Offerta 
	 * 
	 * @precondition storicoOfferte!=null
	 * @postcondition storicoOfferte.size()==storicoOfferte'.size()
	 */
	public ArticoloState statoOffertaAttuale() {
		return this.storicoOfferte.get(this.storicoOfferte.size()-1).getOfferta();
	}
	
	public void aperta() {
		this.state.aperta();
	}

	public void chiusa() {
		this.state.chiusa();
	}

	public void inScambio() {
		this.state.inScambio();
	}

	public void ritirata() {
		this.state.ritirata();
	}

	public void accoppiata() {
		this.state.accoppiata();
	}

	public void selezionata() {
		this.state.selezionata();
	}

	public void cambiaStato(ArticoloState state) {
		this.state = state;
		storicoOfferte.add(new StatoArticolo(LocalDateTime.now(), state));
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

	public Collection<CampoCompilabile> getCampi() {
		return campi;
	}
}
