package it.unibs.elaborato.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.categoria.TipoDato;
import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.mvc.controller.Controller;
import it.unibs.elaborato.mvc.model.Model;
import it.unibs.elaborato.mvc.view.View;


class TestBlackBox {

	//test blackbox metodo
	@Test
	public void testBlackBoxCampiCategoria() throws UsernameException, PasswordException {
		ListaCategorie categorie = new ListaCategorie();
	    ArrayList<Campo> campiPadre=new ArrayList<Campo>();
	    ArrayList<Campo> campiFoglia=new ArrayList<Campo>();
	    
	    campiPadre.add(new Campo("Campo di prova", TipoDato.DECIMALE, true));
	    campiFoglia.add(new Campo("Campo di prova 2", TipoDato.DECIMALE, true));
	    
	    Categoria c = new Categoria("Categoria Test", "Categoria di prova", campiPadre, null);
	    categorie.aggiungi(c);
	    
	    Categoria foglia = new Categoria("Foglia", null, campiFoglia, c);
	    categorie.aggiungi(foglia);
	    
	    assertEquals(categorie.getCampiCategoria(foglia).size(), 4);
	}
	
	@Test
	public void TestUsernameValido() {
		boolean aggiunto = false;
		Model model=new Model();
		Controller controller = new Controller(model, new View());
		controller.carica();

		try {
			aggiunto = model.registrazione("dario", "abcde", "abcde");
		} catch (UsernameException e) {
		} catch (PasswordException e) {
		}
		assertTrue(aggiunto);
	}
	
	@Test
	public void TestUsernameNonValido() {
		boolean aggiunto = false;
		Model model=new Model();
		Controller controller = new Controller(model, new View());
		controller.carica();

		try {
			aggiunto = model.registrazione("1dario", "abcde", "abcde");
		} catch (UsernameException e) {
		} catch (PasswordException e) {
		}
		assertFalse(aggiunto);
	}

	@Test
	public void TestPasswordNonValida() {
		boolean aggiunto = false;
		Model model=new Model();
		Controller controller = new Controller(model, new View());
		controller.carica();

		try {
			aggiunto = model.registrazione("dario", "abc", "abc");
		} catch (UsernameException e) {
		} catch (PasswordException e) {
		}
		assertFalse(aggiunto);
	}
	@Test
	public void TestPasswordValida() {
		boolean aggiunto = false;
		Model model=new Model();
		Controller controller = new Controller(model, new View());
		controller.carica();

		try {
			aggiunto = model.registrazione("dario", "abcdefg", "abcdefg");
		} catch (UsernameException e) {
		} catch (PasswordException e) {
		}
		assertTrue(aggiunto);
	}
	
}
