package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.ListaUtenti;
import it.unibs.elaborato.utente.Password;
import it.unibs.elaborato.utente.Username;
import it.unibs.elaborato.utente.Utente;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class LoginTest {

	@Test
	void loginTest() throws PasswordException, UsernameException {
		ListaUtenti utenti = new ListaUtenti();
		Fruitore f=new Fruitore(new Username("yassiabou"),new Password("aboufaris"));
		utenti.aggiungi(f);
		Utente u = utenti.loginUtente("yassiabou", "aboufaris");

		assertEquals(u.getUsername().getUsername(), "yassiabou");
		assertEquals(u.getPassword().getPassword(), "aboufaris");
	}
	
	@Test
	void signTest() throws PasswordException, UsernameException {;
		ListaUtenti utenti = new ListaUtenti();
		Fruitore f=new Fruitore(new Username("Dario"),new Password("12345678"));
		
		int prima = utenti.size();
		utenti.aggiungi(f);
		
		assertNotEquals(utenti.size(), prima);
	}

}
