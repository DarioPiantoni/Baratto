package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.utente.*;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ListaUtentiTest {

	@Test
	void aggiungiUtenteTest() throws PasswordException, UsernameException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		l.aggiungi(f);
		
		assertEquals(l.size(),3);
	}
	
	@Test
	void aggiungiUtenteDuplicatoTest() throws UsernameException, PasswordException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		Fruitore f2=new Fruitore(new Username("Dario"),new Password("abcdefgh"));
		
		l.aggiungi(f1);
		l.aggiungi(f2);
		
		assertEquals(l.size(),3);
	}
	
	@Test
	void loginUtenteTest() throws PasswordException, UsernameException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		l.aggiungi(f1);
		
		assertNotNull(l.loginUtente("Dario", "12345678"));
		
	}
	
	@Test
	void loginUtenteFallitoTest() throws PasswordException, UsernameException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		l.aggiungi(f1);
		
		assertNull(l.loginUtente("Matteo", "1234"));
		
	}
	
	
	@Test
	void cercaUtenteTest() throws PasswordException, UsernameException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		l.aggiungi(f1);
		
		assertNotEquals(l.cercaUtente("Dario"),-1);
		
	}
	
	@Test
	void cercaUtenteNonPresenteTest() throws PasswordException, UsernameException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		l.aggiungi(f1);
		
		assertEquals(l.cercaUtente("Matteo"),-1);
		
	}
	
	@Test
	void cambioPasswordTest() throws PasswordException, UsernameException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		l.aggiungi(f1);
		
		l.cambioPassword("Dario","abcdefg");
		
		assertNotNull(l.loginUtente("Dario","abcdefg"));
	}
	
	@Test
	void cambioPasswordUtenteNonPresenteTest() throws PasswordException, UsernameException {
		ListaUtenti l=new ListaUtenti();
		Fruitore f1=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		l.aggiungi(f1);
		
		assertFalse(l.cambioPassword("Matteo","abcd"));
	}
	
}
