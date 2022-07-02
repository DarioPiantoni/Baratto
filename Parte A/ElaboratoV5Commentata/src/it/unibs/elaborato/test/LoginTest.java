package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.ListaUtenti;
import it.unibs.elaborato.utente.Utente;
import it.unibs.elaborato.utils.BarattoUtils;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class LoginTest {

	@Test
	void loginTest() {
		System.out.println("Test di login");
		ListaUtenti utenti = BarattoUtils.caricaUtenti();
		Utente u = utenti.loginUtente("yassiabou", "aboufaris");

		assertEquals(u.getUsername(), "yassiabou");
		assertEquals(u.getPassword(), "aboufaris");
	}
	
	@Test
	void signTest() {
		System.out.println("Test di registrazione");
		ListaUtenti utenti = BarattoUtils.caricaUtenti();
		int prima = utenti.size();
		utenti.aggiungiUtente(new Fruitore("test", "123"));
		
		assertNotEquals(utenti.size(), prima);
	}

}
