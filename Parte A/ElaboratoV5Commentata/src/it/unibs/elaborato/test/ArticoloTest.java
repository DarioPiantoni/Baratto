package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.Offerta;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utils.BarattoUtils;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ArticoloTest {

	@Test
	void addTest() {
		ListaArticoli articoli = BarattoUtils.caricaArticoli();
		ListaCategorie categorie = BarattoUtils.caricaCategorie();
		Categoria c = new Categoria("Categoria di test", "Descrizione della categoria di test", new ArrayList<>(), null);
		categorie.aggiungiCategoria(c);
		
		Articolo articolo = new Articolo(new Fruitore("yassiabou", "aboufaris"), "Articolo di test", c, null);
		articoli.aggiungiArticolo(articolo);
		
		HashMap<Integer,Articolo> l = articoli.getLista();
		articoli.getArticolo(l.size()-1).cambioStatoOfferta(Offerta.APERTA);
		
		assertTrue(l.containsValue(articolo));
		assertEquals(articoli.getArticolo(l.size()-1).statoOffertaAttuale(), Offerta.APERTA);
	}
	
	@Test
	void ritiraTest() {
		ListaArticoli articoli = BarattoUtils.caricaArticoli();
		ListaCategorie categorie = BarattoUtils.caricaCategorie();
		Categoria c = new Categoria("Categoria di test", "Descrizione della categoria di test", new ArrayList<>(), null);
		categorie.aggiungiCategoria(c);
		
		Articolo articolo = new Articolo(new Fruitore("yassiabou", "aboufaris"), "Articolo di test", c, null);
		articoli.aggiungiArticolo(articolo);
		HashMap<Integer,Articolo> l = articoli.getLista();
		
		articoli.getArticolo(l.size()-1).cambioStatoOfferta(Offerta.RITIRATA);
		
		assertEquals(articoli.getArticolo(l.size()-1).statoOffertaAttuale(), Offerta.RITIRATA);
	}


}
