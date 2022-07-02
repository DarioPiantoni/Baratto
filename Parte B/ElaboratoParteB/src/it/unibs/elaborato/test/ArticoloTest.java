package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mvc.controller.Controller;
import it.unibs.elaborato.mvc.model.Model;
import it.unibs.elaborato.mvc.view.View;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.Password;
import it.unibs.elaborato.utente.Username;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ArticoloTest {

	private Model model = new Model();
	private View view = new View();
	private Controller controller = new Controller(model, view);
	
	@Test
	void addTest() throws UsernameException, PasswordException {
		controller.carica();
		ListaArticoli articoli = controller.getArticoli();
		ListaCategorie categorie = controller.getCategorie();
		Categoria c = new Categoria("Categoria di test", "Descrizione della categoria di test", new ArrayList<>(), null);
		categorie.aggiungi(c);
		
		Articolo articolo = new Articolo(new Fruitore(new Username("yassiabou"), new Password("aboufaris")), "Articolo di test", c, null);
		articoli.aggiungi(articolo);
		
		HashMap<Integer,Articolo> l = (HashMap<Integer, Articolo>) articoli.getLista();
		
		assertTrue(l.containsValue(articolo));
		assertEquals(articoli.getArticolo(l.size()).statoOffertaAttuale().toString(), "APERTA");
	}
	
	@Test
	void ritiraTest() throws UsernameException, PasswordException {
		controller.carica();
		ListaArticoli articoli = controller.getArticoli();
		ListaCategorie categorie = controller.getCategorie();
		Categoria c = new Categoria("Categoria di test", "Descrizione della categoria di test", new ArrayList<>(), null);
		categorie.aggiungi(c);
		
		Articolo articolo = new Articolo(new Fruitore(new Username("yassiabou"), new Password("aboufaris")), "Articolo di test", c, null);
		articoli.aggiungi(articolo);
		
		HashMap<Integer,Articolo> l = (HashMap<Integer, Articolo>) articoli.getLista();
		
		articoli.getArticolo(l.size()).ritirata();;
		
		assertEquals(articoli.getArticolo(l.size()).statoOffertaAttuale().toString(), "RITIRATA");
	}


}
