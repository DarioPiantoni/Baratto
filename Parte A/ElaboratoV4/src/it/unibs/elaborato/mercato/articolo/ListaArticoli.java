package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;
import java.util.HashMap;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.utente.Fruitore;

public class ListaArticoli implements Serializable{
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,Articolo> articoli;
	private int id;

	public ListaArticoli() {
		super();
		this.articoli=new HashMap<Integer,Articolo>();
		id=1;
	}
	
	public HashMap<Integer, Articolo> getLista() {
		return articoli;
	}
	
	public void aggiungiArticolo(Articolo a) {
		this.articoli.put(id,a);
		id++;
	}

	public Articolo getArticolo(int id) {
		return this.articoli.get(id);
	}
	
	public HashMap<Integer,Articolo> getArticoliAttivi(Categoria c){
		HashMap<Integer,Articolo> articoliAttivi=new HashMap<Integer,Articolo>();
		
		for(int chiave:this.articoli.keySet())
			if(articoli.get(chiave).statoOffertaAttuale().equals(Offerta.APERTA) && articoli.get(chiave).getCategoria().equals(c))
				articoliAttivi.put(chiave,articoli.get(chiave));
		return articoliAttivi;
	}
	
	public HashMap<Integer,Articolo> getArticoliFruitore(Fruitore f){
		HashMap<Integer,Articolo> articoliFruitore=new HashMap<Integer,Articolo>();
		
		for(int chiave:articoli.keySet())
			if(articoli.get(chiave).getProprietario().equals(f))
				articoliFruitore.put(chiave,articoli.get(chiave));
		return articoliFruitore;
	}
	
	public HashMap<Integer,Articolo> getArticoliAttiviFruitore(Fruitore f){
		HashMap<Integer,Articolo> articoliAttiviFruitore=new HashMap<Integer,Articolo>();
		
		for(int chiave:articoli.keySet())
			if(articoli.get(chiave).getProprietario().equals(f) && articoli.get(chiave).statoOffertaAttuale().equals(Offerta.APERTA))
				articoliAttiviFruitore.put(chiave,articoli.get(chiave));
		return articoliAttiviFruitore;
	}
	
	public HashMap<Integer,Articolo> getArticoliAttiviCategoriaNoFruitore(Categoria c,Fruitore f){
		HashMap<Integer,Articolo> articoliAttiviNoFruitore=new HashMap<Integer,Articolo>();
		
		for(int chiave:articoli.keySet())
			if(!articoli.get(chiave).getProprietario().equals(f) && articoli.get(chiave).statoOffertaAttuale().equals(Offerta.APERTA) && articoli.get(chiave).getCategoria().equals(c))
				articoliAttiviNoFruitore.put(chiave,articoli.get(chiave));
		return articoliAttiviNoFruitore;
	}

	@Override
	public String toString() {
		StringBuilder testo=new StringBuilder("Lista articoli");
		for(Articolo a:this.articoli.values()){
			testo.append(a.toString()+"\n");
		}
		
		return testo.toString();
		
	}
	
	
	
}
