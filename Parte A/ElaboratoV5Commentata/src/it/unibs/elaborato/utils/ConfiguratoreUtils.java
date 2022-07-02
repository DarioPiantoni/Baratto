package it.unibs.elaborato.utils;

import java.time.LocalTime;
import java.util.ArrayList;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.categoria.TipoDato;
import it.unibs.elaborato.mercato.Giorno;
import it.unibs.elaborato.mercato.IntervalloOrario;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.Scambio;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
/**Classe di utilità che permette al configuratore di effetturare operazioni di setting sul sistema
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class ConfiguratoreUtils {

	// costanti menù configuratore
	private static final String[] VOCI_MENU_CONFIGURATORE = { "Visualizza le categorie", "Visualizza luoghi",
			"Visualizza articoli attivi di una categoria", "Visualizza le offerte in scambio o chiuse" };
	//private static final int INSERISCI_CATEGORIA_CONFIGURATORE = 1;
	private static final int VISUALIZZA_CATEGORIA_CONFIGURATORE = 1;
	// private static final int INSERISCI_LUOGO_CONFIGURATORE = 3;
	private static final int VISUALIZZA_LUOGHI_CONFIGURATORE = 2;
	private static final int VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_CONFIGURATORE = 3;
	private static final int VISUALIZZA_OFFERTE_IN_SCAMBIO_CHIUSE_CONFIGURATORE = 4;

	private static final String TITOLO_MENU = "Baratto";
	private static final int ESCI = 0;

	/**
	 * Metodo che visualizza a video il menu di interazione per il configuratore
	 * @param categorie lista delle categorie 
	 * @param punto lista dei punti di interesse
	 * @param articoli lista degli articoli
	 * @param scambi lista degli scambi
	 */
	public static void menuConfiguratore(ListaCategorie categorie, PuntoInteresse punto,ListaArticoli articoli, ListaScambi scambi) {
		MyMenu menu = new MyMenu(TITOLO_MENU, VOCI_MENU_CONFIGURATORE);
		int risposta;
		do {
			risposta = menu.scegli();

			switch (risposta) {
			/*
			   case INSERISCI_CATEGORIA_CONFIGURATORE: 
			   inserisciCategoria(categorie); 
			   break;
			 */
			case VISUALIZZA_CATEGORIA_CONFIGURATORE:
				visualizzaCategorie(categorie);
				break;
			/*
			   case INSERISCI_LUOGO_CONFIGURATORE: 
			   if(punto==null)
					punto=inserisciLuogo();
			   else
					System.out.println("Il punto di interesse è già stato inserito");
			   break;
			 */
			case VISUALIZZA_LUOGHI_CONFIGURATORE:
				BarattoUtils.visualizzaLuoghi(punto);
				break;
			case VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_CONFIGURATORE:
				BarattoUtils.visualizzaArticoliAttiviCategoria(categorie, articoli);
				break;
			case VISUALIZZA_OFFERTE_IN_SCAMBIO_CHIUSE_CONFIGURATORE:
				visualizzaOffertaCategoria(categorie, articoli, scambi);
				break;
			case ESCI:
				System.out.println("Arrivederci");
				break;
			default:
				System.out.println("Voce di menù non valida");
			}
		} while (risposta != ESCI);
	}
	
	/**
	 * Metodo che visualizza le offerte relative ad una categoria scelta dall'utente 
	 * @param categorie  lista delle categorie
	 * @param articoli lista degli articoli
	 * @param scambi lista degli scambi
	 */
	private static void visualizzaOffertaCategoria(ListaCategorie categorie, ListaArticoli articoli,ListaScambi scambi) {
		Categoria categoriaScelta = BarattoUtils.scegliCategoria(categorie);
		if(categoriaScelta!=null) {
			ArrayList<Scambio> offerte = scambi.getOfferteCategoria(categoriaScelta, articoli);
			if(!offerte.isEmpty()) {
				for (Scambio s : offerte)
					System.out.println(s.visualizza(articoli));
			}else
				System.out.println("Non ci sono offerte per la categoria "+categoriaScelta.getNome());
		}
	}

	/**
	 * Metodo che visualizza a video tutte le categorie presenti nel sistema
	 * @param categorie lista delle categorie
	 */
	private static void visualizzaCategorie(ListaCategorie categorie) {
		System.out.println(categorie.toString());
	}

	// Metodo non utilizzato nella versione 5
	@SuppressWarnings("unused")
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

		PuntoInteresse punto = new PuntoInteresse(piazza, luoghi, giorni, intervalliOrari, giorniMax);
		return punto;
	}
	// Metodo non utilizzato nella versione 5
	private static IntervalloOrario inserisciIntervallo() {
		System.out.println("Inserisci l'ora di inizio");
		LocalTime inizio = inserisciOra();

		System.out.println("Inserisci l'ora di fine");
		LocalTime fine = inserisciOra();

		IntervalloOrario orario = new IntervalloOrario(inizio, fine);
		return orario;
	}
	// Metodo non utilizzato nella versione 5
	private static LocalTime inserisciOra() {
		int ora = InputDati.leggiIntero("Inserisci un'ora\n", 0, 24);

		int minuti;
		do {
			minuti = InputDati.leggiIntero("Inserisci i minuti\n", 0, 60);
			if (minuti != 0 && minuti != 30)
				System.out.println("Errore, devi inserire un orario con i minuti a 0 o a 30");
		} while (minuti != 0 && minuti != 30);
		LocalTime tempo = LocalTime.of(ora, minuti);
		return tempo;
	}
	
	
	// Metodo non utilizzato nella versione 5
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

	// Metodo non utilizzato nella versione 5
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
	
	// Metodo non utilizzato nella versione 5
	private static ArrayList<Campo> inserisciCampi() {
		String scelta;
		ArrayList<Campo> campi = new ArrayList<Campo>();

		System.out.println("Inserisci i campi compilabili della categoria");
		do {
			scelta = InputDati.leggiStringaNonVuota("Vuoi inserire campi compilabili?(Sì/No)\n");
			if (scelta.equalsIgnoreCase("Sì")) {
				Campo nuovoCampo = inserisciCampo();
				campi.add(nuovoCampo);
			}

		} while (scelta.equalsIgnoreCase("Sì"));
		return campi;
	}
	// Metodo non utilizzato nella versione 5
	private static Campo inserisciCampo() {
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
	
	// Metodo non utilizzato nella versione 5
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

	// Metodo non utilizzato nella versione 5
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

	// Metodo non utilizzato nella versione 5
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
}
