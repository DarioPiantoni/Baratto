package it.unibs.elaborato.prova;

import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.utente.Configuratore;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.ListaUtenti;
import it.unibs.elaborato.utente.Utente;
import it.unibs.elaborato.utils.BarattoUtils;
import it.unibs.elaborato.utils.ConfiguratoreUtils;
import it.unibs.elaborato.utils.FruitoreUtils;
import it.unibs.elaborato.utils.InputDati;
import it.unibs.elaborato.utils.MyMenu;
/**Classe Main del sistema 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class BarattoMain {
	// costanti menù login
	private static final String TITOLO_MENU_LOGIN = "Accedi/Registrati";
	private static final String[] VOCI_MENU_LOGIN = { "Login", "Registrati" };
	private static final int LOGIN = 1;
	private static final int REGISTRAZIONE = 2;

	// costanti menù baratto
	private static final String MESSAGGIO_BENVENUTO = "Buongiorno, %s\n";
	private static final int ESCI = 0;

	private static PuntoInteresse punto;
	
	public static void main(String[] args) {
		ListaUtenti utenti;
		ListaCategorie categorie;
		ListaArticoli articoli;
		ListaScambi scambi;

		// carico dai file
		utenti = BarattoUtils.caricaUtenti();
		categorie = BarattoUtils.caricaCategorie();
		punto = BarattoUtils.caricaPuntiInteresse();
		articoli = BarattoUtils.caricaArticoli();
		scambi = BarattoUtils.caricaScambi();

		// controllo le scadenze degli scambi
		BarattoUtils.controllaScadenze(punto, articoli, scambi);
		
		//login o registrazione
		accesso(utenti, categorie, articoli, scambi);

		// salvo i dati su file
		BarattoUtils.salva(utenti, articoli, scambi);

	}
	
	/**
	 * metodo che visualizza a video il menu di accesso 
	 * @param utenti lista degli utenti
	 * @param categorie lista delle categorie
	 * @param puntiInteresse lista dei punti di interesse
	 * @param articoli lista degli articoli
	 * @param scambi lista degli scambi
	 */
	private static void accesso(ListaUtenti utenti, ListaCategorie categorie, ListaArticoli articoli, ListaScambi scambi) {
		boolean login;
		MyMenu menuLogin = new MyMenu(TITOLO_MENU_LOGIN, VOCI_MENU_LOGIN);
		int rispostaLogin;
		do {
			login = false;
			rispostaLogin = menuLogin.scegli();
			switch (rispostaLogin) {
			case LOGIN:
				login(utenti, categorie, articoli, scambi, login);
				break;
			case REGISTRAZIONE:
				registrazione(utenti, login);
				break;
			case ESCI:
				System.out.println("Grazie e arrivederci");
				break;
			default:
				System.out.println("Voce di menù non valida");
			}
		} while (rispostaLogin != ESCI);
	}
	/**
	 * metodo che permette ad un utente di effettuare il login 
	 * @param utenti
	 * @param categorie
	 * @param puntiInteresse
	 * @param articoli
	 * @param scambi
	 * @param login
	 */
	private static void login(ListaUtenti utenti, ListaCategorie categorie, ListaArticoli articoli, ListaScambi scambi, boolean login) {
		String username;
		String password;
		String nuovaPassword;
		String nuovaPasswordConferma;
		Utente utenteLogin;
		do {
			username = InputDati.leggiStringaNonVuota("Inserisci il tuo username\n");
			password = InputDati.leggiStringaNonVuota("Inserisci la password\n");

			utenteLogin = utenti.loginUtente(username, password);

			if (utenteLogin != null) {
				if (BarattoUtils.isPrimoAccesso(utenteLogin)) {
					do {
						nuovaPassword = InputDati.leggiStringaNonVuota("Inserisci una nuova password\n");
						nuovaPasswordConferma = InputDati.leggiStringaNonVuota("Conferma la nuova password\n");

						if (nuovaPassword.equals(nuovaPasswordConferma))
							utenti.cambioPassword(username, nuovaPassword);
						else
							System.out.println("Non coincidono le password da te inserite");

					} while (!nuovaPassword.equals(nuovaPasswordConferma));
				}
				System.out.printf(MESSAGGIO_BENVENUTO, username);
				login = true;
			} else
				System.out.println("Username o password errata");

		} while (!login);

		// scelgo tra configuratore e fruitore
		if (utenteLogin instanceof Configuratore)
			ConfiguratoreUtils.menuConfiguratore(categorie, punto, articoli,scambi);
		if (utenteLogin instanceof Fruitore)
			FruitoreUtils.menuFruitore(categorie, punto, articoli, scambi, utenteLogin);
	}
	
	/**
	 * metodo che permette ad un nuovo utente di registrarsi
	 * @param utenti lista degli utenti
	 * @param login TRUE se il login è stato effettuao correttamente 
	 */
	private static void registrazione(ListaUtenti utenti, boolean login) {
		String username;
		String nuovaPassword;
		String nuovaPasswordConferma;
		username = InputDati.leggiStringaNonVuota("Inserisci un username\n");

		do {
			nuovaPassword = InputDati.leggiStringaNonVuota("Inserisci una password\n");
			nuovaPasswordConferma = InputDati.leggiStringaNonVuota("Conferma la password\n");

			if (!nuovaPassword.equals(nuovaPasswordConferma))
				System.out.println("Non coincidono le password da te inserite");
		} while (!nuovaPassword.equals(nuovaPasswordConferma));

		Utente nuovoUtente = new Fruitore(username, nuovaPassword);
		boolean aggiunto = utenti.aggiungiUtente(nuovoUtente);
		if (aggiunto == true)
			System.out.println("Registrazione avvenuta con successo");
		else
			System.out.println("Nome utente già scelto");
		login = true;
	}
	
}