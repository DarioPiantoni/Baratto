package it.unibs.elaborato.mercato.articolo;

import java.io.Serializable;
import java.util.HashMap;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.utente.Fruitore;
/**Classe che raggruppa la lista degli articoli inseriti nel sistema dai vari fruitori
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 **/
public class ListaArticoli implements Serializable{
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,Articolo> articoli; // ogni articolo viene salvato in un HashMap che gli associa un' id univoco 
	private int id;
	
	/**
	 * Metodo costruttore che istanzia la lista 
	 */
	public ListaArticoli() {
		super();
		this.articoli=new HashMap<Integer,Articolo>();
		id=1; // imposto l' id del primo articolo che verrà inserito a 1
	}
	
	public HashMap<Integer, Articolo> getLista() {
		return articoli;
	}
	
	/**
	 * Metodo che permettere di aggiungere un articolo alla lista
	 * @param a articolo da aggiungere
	 * 
	 * @precondition a!=null
	 * @postcondition articoli.size()<=articoli'.size() && id<id'
	 */
	public void aggiungiArticolo(Articolo a) {
		this.articoli.put(id,a);
		id++;
	}

	public Articolo getArticolo(int id) {
		return this.articoli.get(id);
	}
	
	/**
	 * Metodo che seleziona gli articoli attivi relativi ad una specifica categoria
	 * @param c categoria sulla quale effetturare la ricerca
	 * @return ritorna un'HashMap contenente gli articoli, ognuno associato al rispettivo id
	 * 
	 * @precondition c!=null
	 * @postcondition articoliAttivi!=null
	 */
	public HashMap<Integer,Articolo> getArticoliAttivi(Categoria c){
		HashMap<Integer,Articolo> articoliAttivi=new HashMap<Integer,Articolo>();
		
		for(int chiave:this.articoli.keySet())
			if(articoli.get(chiave).statoOffertaAttuale().equals(Offerta.APERTA) && articoli.get(chiave).getCategoria().equals(c))
				articoliAttivi.put(chiave,articoli.get(chiave));
		return articoliAttivi;
	}
	
	/**
	 * Metodo che seleziona gli articoli relativi ad uno specifico fruitore
	 * @param f fruitore sul quale effettuare la ricerca
	 * @return ritorna un'HashMap contenente gli articoli, ognuno associato al rispettivo id 
	 * 
	 * @precondition f!=null
	 * @postcondition articoliFruitore!=null
	 */
	public HashMap<Integer,Articolo> getArticoliFruitore(Fruitore f){
		HashMap<Integer,Articolo> articoliFruitore=new HashMap<Integer,Articolo>();
		
		for(int chiave:articoli.keySet())
			if(articoli.get(chiave).getProprietario().equals(f))
				articoliFruitore.put(chiave,articoli.get(chiave));
		return articoliFruitore;
	}
	
	/**
	 * Metodo che seleziona gli articoli ATTIVI relativi ad uno specifico fruitore
	 * @param f fruitore sul quale effetturare la ricerca
	 * @return ritorna un'HashMap contenente gli articoli, ognuno associato al rispettivo id 
	 * 
	 * @precondition f!=null
	 * @postcondition articoliAttiviFruitore!=null
 	 */
	public HashMap<Integer,Articolo> getArticoliAttiviFruitore(Fruitore f){
		HashMap<Integer,Articolo> articoliAttiviFruitore=new HashMap<Integer,Articolo>();
		
		for(int chiave:articoli.keySet())
			if(articoli.get(chiave).getProprietario().equals(f) && articoli.get(chiave).statoOffertaAttuale().equals(Offerta.APERTA))
				articoliAttiviFruitore.put(chiave,articoli.get(chiave));
		return articoliAttiviFruitore;
	}
	
	/**
	 * Metodo che seleziona gli articoli relativi ad una specifica categoria e degli altri fruitori escluso il fruitore passato come parametro
	 * @param c categoria sulla quale effettuare la ricerca
	 * @param f fruitore da escludere
	 * @return ritorna un'HashMap contenente gli articoli, ognuno associato al rispettivo id 
	 * 
	 * @precondition c!=null && f!=null
	 * @postcondition articoliAttiviNoFruitore!=null
 	 */
	public HashMap<Integer,Articolo> getArticoliAttiviCategoriaNoFruitore(Categoria c,Fruitore f){
		HashMap<Integer,Articolo> articoliAttiviNoFruitore=new HashMap<Integer,Articolo>();
		
		for(int chiave:articoli.keySet())
			if(!articoli.get(chiave).getProprietario().equals(f) && articoli.get(chiave).statoOffertaAttuale().equals(Offerta.APERTA) && articoli.get(chiave).getCategoria().equals(c))
				articoliAttiviNoFruitore.put(chiave,articoli.get(chiave));
		return articoliAttiviNoFruitore;
	}
	
	public int size() {
		return this.articoli.size();
	}

	@Override
	public String toString() {
		if(!articoli.isEmpty()) {
			StringBuilder testo=new StringBuilder("Lista articoli");
			for(Articolo a:this.articoli.values()){
				testo.append(a.toString()+"\n");
			}
			
			return testo.toString();
		}else
			return "Non sono presenti articoli";
		
	}
	
	
	
}
