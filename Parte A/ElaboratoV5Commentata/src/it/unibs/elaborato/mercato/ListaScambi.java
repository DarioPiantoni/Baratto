package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.Offerta;
import it.unibs.elaborato.utente.Fruitore;

/**Classe che raggruppa la lista di scambi effettuati e in corso 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 * @invariant this.scambi != null
 */
public class ListaScambi implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Scambio> scambi;
	
	/**
	 * Metodo costruttore che istanzia l'array 
	 */
	public ListaScambi() {
		super();
		scambi=new ArrayList<Scambio>();
	}
	
	/**
	 * Metodo che permette di aggiungere uno scambio alla lista
	 * @param s oggetto scambio da aggiungere
	 * 
	 * @precondition s!=null
	 * @postcondition scambi.size() <= scambi'.size() 
	 */
	public void aggiungiScambio(Scambio s) {
		scambi.add(s);
	}
	
	/**
	 * Metodo che permette di eliminare uno scambio dalla lista
	 * @param s scambio da eliminare
	 * @return ritorna TRUE se è stato possibile eliminare lo scambio
	 * 
	 * @precondition s!=null
	 * @postcondition scambi.size()>= scambi'.size() 
	 */
	public boolean eliminaScambio(Scambio s) {
		return scambi.remove(s);
	}
	
	/**
	 * Metodo che controlla se ci sono scambi scaduti nella lista e in tal caso li elimina
	 * @param punto lista dei punti di interesse configurati
	 * @param articoli lista degli articoli inseriti nel sistema
	 * 
	 * @precondition punto!=null && articoli!=null
	 * @postcondition scambi.size()>=scambi'.size() 
	 */
	public void controllaScadenza(PuntoInteresse punto, ListaArticoli articoli) {
        ArrayList<Scambio> eliminati = new ArrayList<Scambio>();
        for (Scambio s : scambi) {
                if (s.getDataScadenza(punto, articoli).isBefore(LocalDateTime.now())
                                && (articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.ACCOPPIATA)
                                || articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().equals(Offerta.SELEZIONATA)
                                || articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO)
                                || articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO)))
                        eliminati.add(s);
        }

        for (Scambio s : eliminati) {
                articoli.getArticolo(s.getIdArticoloA()).cambioStatoOfferta(Offerta.APERTA);
                articoli.getArticolo(s.getIdArticoloB()).cambioStatoOfferta(Offerta.APERTA);
                eliminaScambio(s);
        }
	}
	
	/**
	 * Metodo che seleziona dalla lista gli scambi ancora attivi relativi a un fruitore
	 * @param fruitore fruitore sul quale effettuare la ricerca
	 * @param articoli lista degli articoli presenti nel sistema 
	 * @return ritorna un array di scambi
	 * 
	 * @precondition fruitore!=null && articoli!=null
	 * @postcondition scambiAttivi!=null && this.scambi = this.scambi'
	 */
	public ArrayList<Scambio> getScambiAttivi(Fruitore fruitore,ListaArticoli articoli) {
		ArrayList<Scambio> scambiAttivi=new ArrayList<Scambio>();
		for(Scambio s:scambi) {
			if(articoli.getArticolo(s.getIdArticoloA()).getProprietario().equals(fruitore) || articoli.getArticolo(s.getIdArticoloB()).getProprietario().equals(fruitore))
				scambiAttivi.add(s);
		}
		return scambiAttivi;
	}
	
	/**
	 * Metodo che seleziona gli Scambi attivi a cui uno specifico fruitore può rispondere
	 * @param fruitore fruitore sul quale effettuare la ricerca
	 * @param articoli lista di articoli presenti nel sistema
	 * @return ritorna un array di scambi
	 * 
	 * @precondition fruitore!=null && articoli!=null
	 * @postcondition proposte!=null && this.scambi = this.scambi'
	 */
	public ArrayList<Scambio> getProposte(Fruitore fruitore,ListaArticoli articoli){
		ArrayList<Scambio> proposte=new ArrayList<Scambio>();
		
		for(Scambio s:scambi) {
			if(s.getToken().equals(Token.A) && articoli.getArticolo(s.getIdArticoloA()).getProprietario().equals(fruitore) && !articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.CHIUSA))
				proposte.add(s);
			if(s.getToken().equals(Token.B) && articoli.getArticolo(s.getIdArticoloB()).getProprietario().equals(fruitore) && !articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().equals(Offerta.CHIUSA))
				proposte.add(s);
		}
		
		return proposte;
	}
	
	/**
	 * Metodo che seleziona le offerte avanzate da uno specifico fruitore
	 * @param fruitore fruitore sul quale effettuare la ricerca 
	 * @param articoli lista degli articoli presenti nel sistema
	 * @return ritorna un array di scambi
	 * 
	 * @precondition fruitore!=null && articoli!=null
	 * @postcondition offerteFruitore!=null && this.scambi = this.scambi'
	 */
	public ArrayList<Scambio> getOfferteFruitore(Fruitore fruitore,ListaArticoli articoli){
		ArrayList<Scambio> offerteFruitore=new ArrayList<Scambio>();
		for(Scambio s:scambi) {
			if(articoli.getArticolo(s.getIdArticoloA()).getProprietario().equals(fruitore))
				offerteFruitore.add(s);
		}
		
		return offerteFruitore;
	}
	
	/**
	 * Metodo che seleziona gli scambi relativi ad una specifica categoria
	 * @param categoria categoria nella quale effettuare la ricerca
	 * @param articoli lista degli articoli presenti nel sistema 
	 * @return ritorna un array di scambi
	 * 
	 * @precondition categoria!=null && articoli!=null
	 * @postcondition offerteCategoria!=null && this.scambi = this.scambi'
	 */
	public ArrayList<Scambio> getOfferteCategoria(Categoria categoria,ListaArticoli articoli){
		ArrayList<Scambio> offerteCategoria=new ArrayList<Scambio>();
		for(Scambio s:scambi) {
			if(articoli.getArticolo(s.getIdArticoloA()).getCategoria().equals(categoria) && (articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO) || articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.CHIUSA)))
				offerteCategoria.add(s);
		}
		return offerteCategoria;
	}
	
	public int size() {
		return this.scambi.size();
	}
	
}
