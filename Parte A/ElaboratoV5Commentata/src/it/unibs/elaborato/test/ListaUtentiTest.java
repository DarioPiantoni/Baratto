package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.utente.*;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ListaUtentiTest {

	@Test
	void aggiungiUtenteTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f=new Fruitore("Dario","1234");
		
		l.aggiungiUtente(f);
		
		assertEquals(l.size(),3);
	}
	
	@Test
	void aggiungiUtenteDuplicatoTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore("Dario","1234");
		Fruitore f2=new Fruitore("Dario","abcd");
		
		l.aggiungiUtente(f1);
		l.aggiungiUtente(f2);
		
		assertEquals(l.size(),3);
	}
	
	@Test
	void loginUtenteTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore("Dario","1234");
		
		l.aggiungiUtente(f1);
		
		assertNotNull(l.loginUtente("Dario", "1234"));
		
	}
	
	@Test
	void loginUtenteFallitoTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore("Dario","1234");
		
		l.aggiungiUtente(f1);
		
		assertNull(l.loginUtente("Matteo", "1234"));
		
	}
	
	
	@Test
	void cercaUtenteTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore("Dario","1234");
		
		l.aggiungiUtente(f1);
		
		assertNotEquals(l.cercaUtente("Dario"),-1);
		
	}
	
	@Test
	void cercaUtenteNonPresenteTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore("Dario","1234");
		
		l.aggiungiUtente(f1);
		
		assertEquals(l.cercaUtente("Matteo"),-1);
		
	}
	
	@Test
	void cambioPasswordTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore("Dario","1234");
		
		l.aggiungiUtente(f1);
		
		l.cambioPassword("Dario","abcd");
		
		assertNotNull(l.loginUtente("Dario","abcd"));
	}
	
	@Test
	void cambioPasswordUtenteNonPresenteTest() {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore("Dario","1234");
		
		l.aggiungiUtente(f1);
		
		assertFalse(l.cambioPassword("Matteo","abcd"));
	}
	
}
