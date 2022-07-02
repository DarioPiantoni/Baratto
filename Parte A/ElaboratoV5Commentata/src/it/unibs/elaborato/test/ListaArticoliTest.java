package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.mercato.articolo.*;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ListaArticoliTest {

	@Test
	void aggiungiArticoloTest() {
		Fruitore f=new Fruitore("Dario","1234");
		Categoria c=new Categoria("Telefono",null,null,null);
		Articolo a=new Articolo(f,"Samsung S5",c,null);
		a.cambioStatoOfferta(Offerta.APERTA);
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungiArticolo(a);
		
		assertEquals(l.size(),1);	
	}
	
	@Test
	void getArticoliAttiviTest() {
		Fruitore f=new Fruitore("Dario","1234");
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f,"Samsung S5",c1,null);
		a1.cambioStatoOfferta(Offerta.APERTA);
		
		Articolo a2=new Articolo(f,"Lavatrice",c1,null);
		a2.cambioStatoOfferta(Offerta.APERTA);
		
		Articolo a3=new Articolo(f,"Romanzo",c2,null);
		a3.cambioStatoOfferta(Offerta.APERTA);
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungiArticolo(a1);
		l.aggiungiArticolo(a2);
		l.aggiungiArticolo(a3);
		
		assertEquals(l.getArticoliAttivi(c1).size(),2);	
		assertEquals(l.getArticoliAttivi(c2).size(),1);	
	}
	
	@Test
	void getArticoliFruitoreTest() {
		Fruitore f1=new Fruitore("Dario","1234");
		Fruitore f2=new Fruitore("Yassine","abcd");
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f1,"Samsung S5",c1,null);
		a1.cambioStatoOfferta(Offerta.RITIRATA);
		
		Articolo a2=new Articolo(f2,"Lavatrice",c1,null);
		a2.cambioStatoOfferta(Offerta.APERTA);
		
		Articolo a3=new Articolo(f1,"Romanzo",c2,null);
		a3.cambioStatoOfferta(Offerta.ACCOPPIATA);
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungiArticolo(a1);
		l.aggiungiArticolo(a2);
		l.aggiungiArticolo(a3);
		
		assertEquals(l.getArticoliFruitore(f1).size(),2);
		assertEquals(l.getArticoliFruitore(f2).size(),1);
		
	}
	
	@Test
	void getArticoliAttiviFruitoreTest() {
		Fruitore f1=new Fruitore("Dario","1234");
		Fruitore f2=new Fruitore("Yassine","abcd");
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f1,"Samsung S5",c1,null);
		a1.cambioStatoOfferta(Offerta.APERTA);
		
		Articolo a2=new Articolo(f2,"Lavatrice",c1,null);
		a2.cambioStatoOfferta(Offerta.APERTA);
		
		Articolo a3=new Articolo(f1,"Romanzo",c2,null);
		a3.cambioStatoOfferta(Offerta.APERTA);
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungiArticolo(a1);
		l.aggiungiArticolo(a2);
		l.aggiungiArticolo(a3);
		
		assertEquals(l.getArticoliAttiviFruitore(f1).size(),2);
		assertEquals(l.getArticoliAttiviFruitore(f2).size(),1);
		
	}
	
	@Test
	void getArticoliAttiviCategoriaNoFruitoreTest() {
		Fruitore f1=new Fruitore("Dario","1234");
		Fruitore f2=new Fruitore("Yassine","abcd");
		Categoria c1=new Categoria("Telefono",null,null,null);
		Categoria c2=new Categoria("Libro",null,null,null);
		Articolo a1=new Articolo(f1,"Samsung S5",c1,null);
		a1.cambioStatoOfferta(Offerta.APERTA);
		
		Articolo a2=new Articolo(f2,"Lavatrice",c1,null);
		a2.cambioStatoOfferta(Offerta.APERTA);
		
		Articolo a3=new Articolo(f1,"Romanzo",c2,null);
		a3.cambioStatoOfferta(Offerta.APERTA);
		
		
		ListaArticoli l=new ListaArticoli();
		
		l.aggiungiArticolo(a1);
		l.aggiungiArticolo(a2);
		l.aggiungiArticolo(a3);
		
		assertEquals(l.getArticoliAttiviCategoriaNoFruitore(c1,f1).size(),1);
		assertEquals(l.getArticoliAttiviCategoriaNoFruitore(c1,f2).size(),1);
		
	}


}
