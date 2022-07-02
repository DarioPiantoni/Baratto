package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.Password;
import it.unibs.elaborato.utente.Username;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ListaArticoliTest {

	@Test
	void aggiungiArticoloTest() throws UsernameException, PasswordException {
		Fruitore f=new Fruitore(new Username("Dario"),new Password("12345678"));
		Categoria c=new Categoria("Telefono",null,null,null);
		Articolo a=new Articolo(f,"Samsung S5",c,null);
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungi(a);
		
		assertEquals(l.size(),1);	
	}
	
	@Test
	void getArticoliAttiviTest() throws UsernameException, PasswordException {
		Fruitore f=new Fruitore(new Username("Dario"),new Password("12345678"));
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f,"Samsung S5",c1,null);
		
		Articolo a2=new Articolo(f,"Lavatrice",c1,null);
		
		Articolo a3=new Articolo(f,"Romanzo",c2,null);
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungi(a1);
		l.aggiungi(a2);
		l.aggiungi(a3);
		
		assertEquals(l.getArticoliAttivi(c1).size(),2);	
		assertEquals(l.getArticoliAttivi(c2).size(),1);	
	}
	
	@Test
	void getArticoliFruitoreTest() throws UsernameException, PasswordException {
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		Fruitore f2=new Fruitore(new Username("Yassine"),new Password("abcdefgh"));
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f1,"Samsung S5",c1,null);
		a1.ritirata();;
	
		Articolo a2=new Articolo(f2,"Lavatrice",c1,null);
		Articolo a3=new Articolo(f1,"Romanzo",c2,null);
		a3.accoppiata();;
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungi(a1);
		l.aggiungi(a2);
		l.aggiungi(a3);
		
		assertEquals(l.getArticoliFruitore(f1).size(),2);
		assertEquals(l.getArticoliFruitore(f2).size(),1);
		
	}
	
	@Test
	void getArticoliAttiviFruitoreTest() throws UsernameException, PasswordException {
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		Fruitore f2=new Fruitore(new Username("Yassine"),new Password("abcdefgh"));
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f1,"Samsung S5",c1,null);
	
		Articolo a2=new Articolo(f2,"Lavatrice",c1,null);
		Articolo a3=new Articolo(f1,"Romanzo",c2,null);
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungi(a1);
		l.aggiungi(a2);
		l.aggiungi(a3);
		
		assertEquals(l.getArticoliAttiviFruitore(f1).size(),2);
		assertEquals(l.getArticoliAttiviFruitore(f2).size(),1);
		
	}
	
	@Test
	void getArticoliAttiviCategoriaNoFruitoreTest() throws UsernameException, PasswordException {
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		Fruitore f2=new Fruitore(new Username("Yassine"),new Password("abcdefgh"));
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f1,"Samsung S5",c1,null);
		Articolo a2=new Articolo(f2,"Lavatrice",c1,null);
		Articolo a3=new Articolo(f1,"Romanzo",c2,null);
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungi(a1);
		l.aggiungi(a2);
		l.aggiungi(a3);
		
		assertEquals(l.getArticoliAttiviCategoriaNoFruitore(c1,f1).size(),1);
		assertEquals(l.getArticoliAttiviCategoriaNoFruitore(c1,f2).size(),1);
		
	}


}
