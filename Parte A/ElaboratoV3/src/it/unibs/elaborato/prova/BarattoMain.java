package it.unibs.elaborato.prova;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.elaborato.categoria.*;
import it.unibs.elaborato.mercato.*;
import it.unibs.elaborato.mercato.articolo.*;
import it.unibs.elaborato.utente.*;
import it.unibs.elaborato.utils.*;

public class BarattoMain {
	// costanti menù login
	private static final String TITOLO_MENU_LOGIN = "Accedi/Registrati";
	private static final String[] VOCI_MENU_LOGIN = { "Login", "Registrati" };
	private static final int LOGIN = 1;
	private static final int REGISTRAZIONE = 2;
	
	// costanti menù baratto
	private static final String MESSAGGIO_BENVENUTO = "Buongiorno, %s\n";
	private static final String TITOLO_MENU = "Baratto";
	private static final int ESCI = 0;
	
	//costanti menù configuratore
	private static final String[] VOCI_MENU_CONFIGURATORE = { "Inserisci una nuova categoria","Visualizza le categorie", "Inserisci un nuovo luogo", "Visualizza luoghi","Visualizza articoli attivi di una categoria" };
	private static final int INSERISCI_CATEGORIA_CONFIGURATORE = 1;
	private static final int VISUALIZZA_CATEGORIA_CONFIGURATORE = 2;
	private static final int INSERISCI_LUOGO_CONFIGURATORE = 3;
	private static final int VISUALIZZA_LUOGHI_CONFIGURATORE = 4;
	private static final int VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_CONFIGURATORE=5;
	
	//costanti menù fruitore
	private static final String[] VOCI_MENU_FRUITORE = { "Visualizza luoghi", "Visualizza categorie", "Inserisci un nuovo articolo",
			"Ritira offerta", "Visualizza i miei articoli","Visualizza articoli attivi di una categoria" };
	private static final int VISUALIZZA_LUOGHI_FRUITORE = 1;
	private static final int VISUALIZZA_CATEGORIA_FRUITORE = 2;
	private static final int INSERISCI_ARTICOLO_FRUITORE=3;
	private static final int RITIRA_OFFERTA_FRUITORE=4;
	private static final int VISUALIZZA_ARTICOLI_FRUITORE=5;
	private static final int VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_FRUITORE=6;

	// costanti per i file
	private static final String PERCORSO_DATI = "src\\it\\unibs\\elaborato\\dati";
	private static final String NOME_FILE_UTENTI = "Utenti.dat";
	private static final String NOME_FILE_CATEGORIE = "Categorie.dat";
	private static final String NOME_FILE_PUNTIINTERESSE="PuntiInteresse.dat";
	private static final String NOME_FILE_ARTICOLI="Articoli.dat";
	
	private static PuntoInteresse punto;

	public static void main(String[] args) {
		String username, password;
		String nuovaPassword, nuovaPasswordConferma;
		Utente utenteLogin;

		MyMenu menuLogin = new MyMenu(TITOLO_MENU_LOGIN, VOCI_MENU_LOGIN);
		int rispostaLogin;

		ListaUtenti utenti;
		ListaCategorie categorie;
		ListaArticoli articoli;

		boolean login;

		// carico dai file
		utenti = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_UTENTI);
		if (utenti == null)
			utenti = new ListaUtenti();
		else
			System.out.println(NOME_FILE_UTENTI + " è stato caricato correttamente");

		categorie = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_CATEGORIE);
		if (categorie == null)
			categorie = new ListaCategorie();
		else
			System.out.println(NOME_FILE_CATEGORIE + " è stato caricato correttamente");
		
		punto=FileUtils.caricaFile(PERCORSO_DATI,NOME_FILE_PUNTIINTERESSE);
		
		articoli=FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_ARTICOLI);
		if(articoli==null)
			articoli=new ListaArticoli();
		else
			System.out.println(NOME_FILE_ARTICOLI+" è stato caricato correttamente");

		do {
			login = false;
			rispostaLogin = menuLogin.scegli();
			switch (rispostaLogin) {
			case LOGIN:
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
					menuConfiguratore(categorie,articoli);
				if (utenteLogin instanceof Fruitore)
					menuFruitore(categorie,articoli,utenteLogin);
				break;
			case REGISTRAZIONE:
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
				break;
			case ESCI:
				System.out.println("Arrivederci");
				break;
			default:
				System.out.println("Voce di menù non valida");
			}
		} while (rispostaLogin != ESCI);

		// salvo i dati su file
		boolean salvatoUtenti = FileUtils.salvaFile(utenti, PERCORSO_DATI, NOME_FILE_UTENTI);
		boolean salvatoCategorie = FileUtils.salvaFile(categorie, PERCORSO_DATI, NOME_FILE_CATEGORIE);
		boolean salvatoPuntiInteresse=FileUtils.salvaFile(punto,PERCORSO_DATI,NOME_FILE_PUNTIINTERESSE);
		boolean salvatoArticoli=FileUtils.salvaFile(articoli,PERCORSO_DATI,NOME_FILE_ARTICOLI);

		if (salvatoUtenti && salvatoCategorie && salvatoPuntiInteresse && salvatoArticoli)
			System.out.println("Salvataggio riuscito");
		else
			System.out.println("Salvataggio non riuscito");

	}

	private static void menuConfiguratore(ListaCategorie categorie, ListaArticoli articoli) {
		MyMenu menu = new MyMenu(TITOLO_MENU, VOCI_MENU_CONFIGURATORE);
		int risposta;
		do {
			risposta = menu.scegli();

			switch (risposta) {
			case INSERISCI_CATEGORIA_CONFIGURATORE:
				inserisciCategoria(categorie);
				break;
			case VISUALIZZA_CATEGORIA_CONFIGURATORE:
				visualizzaCategorie(categorie);
				break;
			case INSERISCI_LUOGO_CONFIGURATORE:
				if(punto==null)
					punto=inserisciLuogo();
				else
					System.out.println("Il punto di interesse è già stato inserito");
				break;
			case VISUALIZZA_LUOGHI_CONFIGURATORE:
				visualizzaLuoghi();
				break;
			case VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_CONFIGURATORE:
				visualizzaArticoliAttiviCategoria(categorie, articoli);
				break;
			case ESCI:
				System.out.println("Arrivederci");
				break;
			default:
				System.out.println("Voce di menù non valida");
			}
		} while (risposta != ESCI);
	}

	private static PuntoInteresse inserisciLuogo() {
		String piazza = InputDati.leggiStringaNonVuota("Inserisci il nome della piazza\n");
		ArrayList<String> luoghi = new ArrayList<String>();
		String continua;
		do {
			String luogo = InputDati.leggiStringaNonVuota("Inserisci il luogo\n");
			luoghi.add(luogo);
			continua = InputDati.leggiStringaNonVuota("Vuoi continuare a inserire luoghi?(Sì/No)\n");
		} while (continua.equalsIgnoreCase("Sì"));

		ArrayList<Giorno> giorni = new ArrayList<Giorno>();
		Giorno giorno = null;
		do {
			switch (InputDati.leggiInteroPositivo("Inserisci il giorno (in numero)\n")) {
			case 1:
				giorno = Giorno.LUNEDI;
				break;
			case 2:
				giorno = Giorno.MARTEDI;
				break;
			case 3:
				giorno = Giorno.MERCOLEDI;
				break;
			case 4:
				giorno = Giorno.GIOVEDI;
				break;
			case 5:
				giorno = Giorno.VENERDI;
				break;
			case 6:
				giorno = Giorno.SABATO;
				break;
			case 7:
				giorno = Giorno.DOMENICA;
				break;
			}
			giorni.add(giorno);
			continua = InputDati.leggiStringaNonVuota("Vuoi continuare a inserire giorno?(Sì/No)\n");

		} while (continua.equalsIgnoreCase("Sì"));

		ArrayList<IntervalloOrario> intervalliOrari = new ArrayList<IntervalloOrario>();
		do {
			intervalliOrari.add(inserisciIntervallo());
			continua = InputDati.leggiStringaNonVuota("Vuoi continuare a inserire degli intervalli orari?(Sì/No)\n");
		} while (continua.equalsIgnoreCase("Sì"));
		
		int giorniMax = InputDati.leggiInteroPositivo("Inserisci il numero massimo di giorni dopo cui l'offerta scade\n");

		PuntoInteresse punto = new PuntoInteresse(piazza, luoghi, giorni, intervalliOrari,giorniMax);
		return punto;
	}

	private static IntervalloOrario inserisciIntervallo() {
		int oraInizio = InputDati.leggiIntero("Inserisci un'ora d'inizio\n", 0, 24);
		
		int minutiInizio;
		do {
			minutiInizio = InputDati.leggiIntero("Inserisci i minuti d'inizio\n", 0, 60);
			if(minutiInizio!=0 && minutiInizio!=30)
				System.out.println("Errore, devi inserire un orario con i minuti a 0 o a 30");
		}while(minutiInizio!=0 && minutiInizio!=30);
		
		int oraFine = InputDati.leggiIntero("Inserisci un'ora di fine\n", 0, 24);
		int minutiFine;
		do {
			minutiFine = InputDati.leggiIntero("Inserisci i minuti di fine\n", 0, 60);
			if(minutiFine!=0 && minutiFine!=30)
				System.out.println("Errore, devi inserire un orario con i minuti a 0 o a 30");
		}while(minutiFine!=0 && minutiFine!=30);
		IntervalloOrario orario = new IntervalloOrario(LocalTime.of(oraInizio, minutiInizio),LocalTime.of(oraFine, minutiFine));
		return orario;
	}

	private static void menuFruitore(ListaCategorie categorie,ListaArticoli articoli,Utente utenteLogin) {
		MyMenu menu = new MyMenu(TITOLO_MENU, VOCI_MENU_FRUITORE);
		int risposta;
		do {
			risposta = menu.scegli();

			switch (risposta) {
			case VISUALIZZA_LUOGHI_FRUITORE:
				visualizzaLuoghi();
				break;
			case VISUALIZZA_CATEGORIA_FRUITORE:
				visualizzaCategorieRadice(categorie);
				break;
			case INSERISCI_ARTICOLO_FRUITORE:
				articoli.aggiungiArticolo(inserisciArticolo(categorie,utenteLogin));
				break;
			case RITIRA_OFFERTA_FRUITORE:
				ritiraOfferta(articoli, utenteLogin);
				break;
			case VISUALIZZA_ARTICOLI_FRUITORE:
				visualizzaArticoliFruitore(articoli, utenteLogin);
				break;
			case VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_FRUITORE:
				visualizzaArticoliAttiviCategoria(categorie, articoli);
				break;
			case ESCI:
				System.out.println("Arrivederci");
				break;
			default:
				System.out.println("Voce di menù non valida");
			}
		} while (risposta != ESCI);
	}

	private static void ritiraOfferta(ListaArticoli articoli, Utente utenteLogin) {
		System.out.println("Scegli un articolo da ritirare");
		cambiaStatoOfferta(articoli, utenteLogin, Offerta.RITIRATA);
	}

	private static int cambiaStatoOfferta(ListaArticoli articoli, Utente utenteLogin, Offerta offerta) {
		int id=selezionaArticolo(articoli, utenteLogin);
		if(id!=-1)
			articoli.getArticolo(id).cambioStatoOfferta(offerta);
		return id;

	}

	private static int selezionaArticolo(ListaArticoli articoli, Utente utenteLogin) {
		HashMap<Integer, Articolo> articoliAttiviFruitore = articoli.getArticoliAttiviFruitore((Fruitore) utenteLogin);
		if (!articoliAttiviFruitore.isEmpty()) {
			StringBuffer testo = new StringBuffer("Scegli un articolo\n");
			ArrayList<Integer> chiavi = new ArrayList<Integer>();

			int i = 0;
			for (int chiave : articoliAttiviFruitore.keySet()) {
				testo.append((i + 1) + "->" + articoliAttiviFruitore.get(chiave).toString() + "\n");
				chiavi.add(chiave);
				i++;
			}
			testo.append("->");

			int indice = InputDati.leggiIntero(testo.toString(), 1, articoliAttiviFruitore.size());

			
			return chiavi.get(indice - 1);
		}

		return -1;
	}

	private static void visualizzaArticoliAttiviCategoria(ListaCategorie categorie, ListaArticoli articoli) {
		Categoria categoria = scegliCategoria(categorie);
		HashMap<Integer, Articolo> articoliCategoria = articoli.getArticoliAttivi(categoria);
		System.out.println("Articoli attivi della categoria " + categoria.getNome());
		for (Articolo a : articoliCategoria.values())
			System.out.println(a.toString());
	}

	private static void visualizzaArticoliFruitore(ListaArticoli articoli, Utente utenteLogin) {
		HashMap<Integer, Articolo> articoliFruitore = articoli.getArticoliFruitore((Fruitore) utenteLogin);
		System.out.println("Articoli di " + utenteLogin.getUsername());
		for (Articolo a : articoliFruitore.values())
			System.out.println(a.toString());
	}

	private static Articolo inserisciArticolo(ListaCategorie categorie,Utente utenteLogin) {
		String nome=InputDati.leggiStringaNonVuota("Inserisci il nome\n");
		Categoria categoria = scegliCategoria(categorie);
		ArrayList<CampoCompilabile> campiCompilabili = inserisciCampiArticolo(categorie,categoria);
		
		Articolo articolo=new Articolo((Fruitore)utenteLogin,nome,categoria,campiCompilabili);
		articolo.cambioStatoOfferta(Offerta.APERTA);
		return articolo;
	}

	private static ArrayList<CampoCompilabile> inserisciCampiArticolo(ListaCategorie categorie,Categoria categoria) {
		ArrayList<Campo> campi=categorie.getCampiCategoria(categoria);
		ArrayList<CampoCompilabile> campiCompilabili=new ArrayList<CampoCompilabile>();
		System.out.println("Inserisci i campi");
		for(Campo c: campi)
		{
			String dato;
			if(c.isObbligatorio())
				dato=InputDati.leggiStringaNonVuota(c.getNome()+"\n");
			else
				dato=InputDati.leggiStringa(c.getNome()+" (FACOLTATIVO)\n");
			campiCompilabili.add(new CampoCompilabile(c,dato));
		}
		return campiCompilabili;
	}

	private static Categoria scegliCategoria(ListaCategorie categorie) {
		StringBuffer testo=new StringBuffer("Scegli una categoria\n");
		ArrayList<Categoria> categorieFoglie=categorie.getCategorieFoglie();
		for(int i=0;i<categorieFoglie.size();i++)
			testo.append((i+1)+"-> "+categorieFoglie.get(i).getNome()+"\n");
		int indiceScelta=InputDati.leggiIntero(testo.toString(),1,categorieFoglie.size());
		Categoria categoria=categorieFoglie.get(indiceScelta-1);
		return categoria;
	}

	private static void inserisciCategoria(ListaCategorie categorie) {
		boolean ok = false;
		System.out.println("Inserisci una categoria");

		do {

			String nomeCategoria = InputDati.leggiStringaNonVuota("Inserisci il nome\n");
			if (controlloRadici(categorie, nomeCategoria))
				ok = false;
			else
				ok = true;

			if (ok) {
				String descrizioneCategoria = InputDati.leggiStringaNonVuota("Inserisci la descrizione\n");
				ArrayList<Campo> campi = inserisciCampi();
				Categoria c = new Categoria(nomeCategoria, descrizioneCategoria, campi, null);
				categorie.aggiungiCategoria(c);
				sottoCategoria(categorie, c, c);
			}

		} while (!ok);
	}

	private static boolean controlloRadici(ListaCategorie categorie, String radice) {
		ArrayList<Categoria> cLista = categorie.getRadici();

		for (Categoria c : cLista) {
			if (c.getNome().equalsIgnoreCase(radice)) {
				System.out.println("Radice già presente");
				return true;
			}
		}

		return false;
	}
	
	private static ArrayList<Campo> inserisciCampi() {
		String scelta;
		ArrayList<Campo> campi = new ArrayList<Campo>();

		System.out.println("Inserisci i campi compilabili della categoria");
		do {
			scelta = InputDati.leggiStringaNonVuota("Vuoi inserire campi compilabili?(Sì/No)\n");
			if (scelta.equalsIgnoreCase("Sì")) {
				Campo nuovoCampo = insersciCampo();
				campi.add(nuovoCampo);
			}

		} while (scelta.equalsIgnoreCase("Sì"));
		return campi;
	}

	private static Campo insersciCampo() {
		String nomeCampo = InputDati.leggiStringaNonVuota("Inserisci il nome campo\n");
		int tipoCampo = InputDati
				.leggiIntero("Inserisci il tipo del campo (1->Parola, 2->Intero, 3->Decimale, 4->Vero/Falso\n", 1, 4);
		TipoDato tipoDato = null;
		switch (tipoCampo) {
		case 1:
			tipoDato = TipoDato.PAROLA;
			break;
		case 2:
			tipoDato = TipoDato.INTERO;
			break;
		case 3:
			tipoDato = TipoDato.DECIMALE;
			break;
		case 4:
			tipoDato = TipoDato.VEROFALSO;
			break;
		}
		String campoOpzionale = InputDati.leggiStringaNonVuota("Il campo è opzionale (Sì/No)\n");
		boolean isOpzionale;
		if (campoOpzionale.equalsIgnoreCase("Sì"))
			isOpzionale = true;
		else
			isOpzionale = false;
		Campo nuovoCampo = new Campo(nomeCampo, tipoDato, isOpzionale);
		return nuovoCampo;
	}

	private static void sottoCategoria(ListaCategorie categorie, Categoria categoriaPadre, Categoria radice) {
		String scelta;
		do {
			scelta = InputDati.leggiStringaNonVuota(
					"Vuoi inserire una sottocategoria per la categoria " + categoriaPadre.getNome() + "? (Sì/No)\n");
			if (scelta.equalsIgnoreCase("Sì")) {
				inserisciSottoCategoria(categorie, categoriaPadre, radice);
			}
		} while (scelta.equalsIgnoreCase("Sì"));
	}

	private static void inserisciSottoCategoria(ListaCategorie categorie, Categoria categoriaPadre, Categoria radice) {
		boolean ok = false;
		System.out.println("Inserisci una categoria");

		do {

			String nomeCategoria = InputDati.leggiStringaNonVuota("Inserisci il nome\n");
			if (controlloSottoCategoria(categorie, radice, nomeCategoria))
				ok = false;
			else
				ok = true;

			if (ok) {
				String descrizioneCategoria = InputDati.leggiStringaNonVuota("Inserisci la descrizione\n");
				ArrayList<Campo> campi = inserisciCampi();
				Categoria c = new Categoria(nomeCategoria, descrizioneCategoria, campi, categoriaPadre);
				categorie.aggiungiCategoria(c);
				sottoCategoria(categorie, c, radice);
			}

		} while (!ok);
	}

	private static boolean controlloSottoCategoria(ListaCategorie categorie, Categoria radice,
			String sottoCategoria) {
		ArrayList<Categoria> cLista = categorie.getAlbero(radice);

		for (Categoria c : cLista) {
			if (c.getNome().equalsIgnoreCase(sottoCategoria)) {
				System.out.println("Categoria già presente");
				return true;
			}
		}

		return false;
	}

	private static void visualizzaCategorie(ListaCategorie categorie) {
		System.out.println(categorie.toString());
	}
	private static void visualizzaCategorieRadice(ListaCategorie categorie) {
		System.out.println(categorie.visualizzaCategorieRadice());
	}
	

	private static void visualizzaLuoghi() {
		if(punto==null) 
			System.out.println("Punto non presente!");
		else
			System.out.println(punto.toString());
	}

}
