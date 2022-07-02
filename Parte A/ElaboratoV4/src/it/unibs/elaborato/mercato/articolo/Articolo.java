package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.utente.Fruitore;

public class Articolo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Fruitore proprietario;
	private String nome;
	private Categoria categoria;
	private ArrayList<CampoCompilabile> campi;
	private ArrayList<StatoArticolo> storicoOfferte;
	
	public Articolo(Fruitore proprietario,String nome, Categoria categoria, ArrayList<CampoCompilabile> campi) {
		super();
		this.proprietario=proprietario;
		this.nome = nome;
		this.categoria = categoria;
		this.campi = campi;
		this.storicoOfferte=new ArrayList<StatoArticolo>();
	}
	
	public void cambioStatoOfferta(Offerta o) {
		this.storicoOfferte.add(new StatoArticolo(LocalDateTime.now(),o));
	}
	
	public Offerta statoOffertaAttuale() {
		return this.storicoOfferte.get(this.storicoOfferte.size()-1).getOfferta();
	}
	
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
