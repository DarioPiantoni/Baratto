package it.unibs.elaborato.mvc.view;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.mercato.Appuntamento;
import it.unibs.elaborato.mercato.Giorno;
import it.unibs.elaborato.mercato.IntervalloOrario;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.Scambio;
import it.unibs.elaborato.mercato.Token;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.CampoCompilabile;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.stato.ArticoloState;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.ListaUtenti;
import it.unibs.elaborato.utente.Utente;
import it.unibs.elaborato.utils.InputDati;
import it.unibs.elaborato.utils.MyMenu;
/**
 * classe VIEW (del modello mcv) che rappresenta il modo in cui l' utente interagisce con l'applicazione
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class View {
	private static final String MESSAGGIO_BENVENUTO = "Buongiorno, %s\n";
	private static final String TITOLO_MENU = "Baratto";
	private static final String[] VOCI_MENU_CONFIGURATORE = { "Visualizza le categorie", "Visualizza luoghi",
			"Visualizza articoli attivi di una categoria", "Visualizza le offerte in scambio o chiuse" };
	private static final String[] VOCI_MENU_FRUITORE = { "Visualizza luoghi", "Visualizza categorie",
			"Inserisci un nuovo articolo", "Ritira offerta", "Visualizza i miei articoli",
			"Visualizza articoli attivi di una categoria", "Effettua una proposta", "Apri casella proposte ricevute",
			"Visualizza le mie offerte" };
	private static final String TITOLO_MENU_PROPOSTA = "Scegli";
	private static final String[] VOCI_MENU_PROPOSTA = { "Conferma la proposta", "Scegli un nuovo appuntamento" };
	private static final String TITOLO_MENU_LOGIN = "Accedi/Registrati";
	private static final String[] VOCI_MENU_LOGIN = { "Login", "Registrati" };
	// Costanti per i file
	private static final String ERRORE_GENERICO = "Errore";
	private static final String ERRORE_LETTURA = "Non è stato possibile leggere il file";
	private static final String ERRORE_CLASSE = "La classe letta dal file non corrisponde alla classe assegnata";
	private static final String ERRORE_CHIUSURA_FILE = "Non è stato possibile chiudere il file";
	private static final String ESTENSIONE_FILE_OGGETTI = ".dat";
	private static final String ERRORE_ESTENSIONE = "L'estensione per i file che con contengono oggetti deve essere "
			+ ESTENSIONE_FILE_OGGETTI;
	private static final String ERRORE_FILE_NON_TROVATO = "Il file %s non è stato trovato\n";
	private static final String ERRORE_SCRITTURA = "Non è stato possibile scrivere il file";
	
	/**
	 * metodo che acquisisce l'username dall'utente 
	 * @return ritorna una stringa contenente l'username
	 */
	public String getUsername() {
		return InputDati.leggiStringaNonVuota("Inserisci il tuo username\n");
	}
	/**
	 * metodo che acquisisce l password dall' utente
	 * @return ritorna una stringa contenete una password
	 */
	public String getPassword() {
		return InputDati.leggiStringaNonVuota("Inserisci la password\n");
	}
	/**
	 * motodo che acquisisce la conferma della password
	 * @return ritorna una stringa contenete la password
	 */
	public String getConfermaPassword() {
		return InputDati.leggiStringaNonVuota("Conferma la nuova password\n");
	}
	/**
	 * metodo che chiede al fruitore di selezionare una categoria tra quelle presenti nel sistema 
	 * @param categorie lista delle categorie presenti nel sistema
	 * @return ritorna la categoria selezionata dall'utente
	 */
	public Categoria getCategoria(ListaCategorie categorie) {
		List<Categoria> categorieFoglie = categorie.getCategorieFoglie();
		Categoria categoria = null;
		if (!categorieFoglie.isEmpty()) {
			StringBuffer testo = new StringBuffer("Scegli una categoria\n");
			for (int i = 0; i < categorieFoglie.size(); i++)
				testo.append((i + 1) + "-> " + categorieFoglie.get(i).getNome() + "\n");
			int indiceScelta = InputDati.leggiIntero(testo.toString(), 1, categorieFoglie.size());
			categoria = categorieFoglie.get(indiceScelta - 1);
		} else
			System.out.println("Non ci sono categorie foglie");

		return categoria;
	}
	/**
	 * metodo che richiede al fruitore di inserire il nome dell'articolo 
	 * @param categorie lista delle categorie presenti nel sistema
	 * @param utenteLogin utente loggato nel sistema
	 * @return ritorna l'articolo aggiunto dall'utente
	 */
	public Articolo getArticolo(ListaCategorie categorie, Utente utenteLogin) {
		String nome = InputDati.leggiStringaNonVuota("Inserisci il nome\n");
		Categoria categoria = getCategoria(categorie);

		Articolo articolo = null;
		if (categoria != null) {
			Collection<CampoCompilabile> campiCompilabili = getCampiArticolo(categorie, categoria);

			articolo = new Articolo((Fruitore) utenteLogin, nome, categoria, campiCompilabili);
		}
		return articolo;
	}
	/**
	 * metodo che richiede al fruitore di inserire i campi relativi al nuovo articolo da aggiungere
	 * @param categorie lista delle categorie presenti nel sistema
	 * @param categoria categoria selezionata dall'utente 
	 * @return ritorna la lista dei campi aggiungi dall'utente
	 */
	public Collection<CampoCompilabile> getCampiArticolo(ListaCategorie categorie, Categoria categoria) {
		Collection<Campo> campi = categorie.getCampiCategoria(categoria);
		Collection<CampoCompilabile> campiCompilabili = new ArrayList<CampoCompilabile>();
		boolean erroreTipo;

		if (!campi.isEmpty()) {
			System.out.println("Inserisci i campi");
			for (Campo c : campi) {
				do {
					erroreTipo = false;
					try {
						String dato;
						if (c.isObbligatorio())
							dato = InputDati.leggiStringaNonVuota(c.getNome() + "\n");
						else
							dato = InputDati.leggiStringa(c.getNome() + " (FACOLTATIVO)\n");
						campiCompilabili.add(new CampoCompilabile(c, dato));
					} catch (NumberFormatException e) {
						System.out.println("Attenzione questo campo è di tipo " + c.getTipo().toString());
						erroreTipo = true;
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
						erroreTipo = true;
					}
				} while (erroreTipo);
			}
		} else
			System.out.println("Non ci sono campi compilabili per questa categoria");

		return campiCompilabili;
	}
	/**
	 * metodo che chiede al fruitore di selezionare un articolo 
	 * @param articoli 
	 * @param utenteLogin
	 * @return indice intero dell'articolo selezionato, -1 se non è andata a buon fine la selezione
	 */
	public int getArticoliAttivi(ListaArticoli articoli, Utente utenteLogin) {
		Map<Integer, Articolo> articoliAttiviFruitore = articoli.getArticoliAttiviFruitore((Fruitore) utenteLogin);
		if (!articoliAttiviFruitore.isEmpty()) {
			StringBuffer testo = new StringBuffer("Scegli un articolo\n");
			ArrayList<Integer> chiavi = new ArrayList<Integer>();

			int i = 0;
			for (int chiave : articoliAttiviFruitore.keySet()) {
				testo.append((i + 1) + "->" + stampaArticolo(articoliAttiviFruitore.get(chiave)) + "\n");
				chiavi.add(chiave);
				i++;
			}
			testo.append("->");

			int indice = InputDati.leggiIntero(testo.toString(), 1, articoliAttiviFruitore.size());

			return chiavi.get(indice - 1);
		}

		return -1;
	}
	/**
	 * metodo che chiede al fruitore di selezionare l' articolo che vuole scambiare
	 * @param articoli lista degli articoli presenti nel sistema
	 * @param utenteLogin utente loggato
	 * @return oggetto Articolo selezionato per lo scambio
	 */
	public int getArticoloA(ListaArticoli articoli, Utente utenteLogin) {
		System.out.println("Scegli un tuo articolo da scambiare");
		int idArticoloA = getArticoliAttivi(articoli, utenteLogin);
		return idArticoloA;
	}
	/**
	 * metodo che chiede al fruitore di selezionare un'articolo con cui effettuare lo scambio
	 * @param articoli lista degli articoli pesenti nel sistema
	 * @param scambi lista degli scambi
	 * @param idArticoloA articolo del fruitore che vuole scambiare
	 * @param utenteLogin utente fruitore loggato
	 * @return ritorna l'articolo selezionato dal fruitore che vuole scambiare
	 */
	public int getArticoloB(ListaArticoli articoli, ListaScambi scambi, int idArticoloA, Utente utenteLogin) {
		int idArticoloB = -1;
		if (idArticoloA != -1) {
			Articolo articoloSelezionato = articoli.getArticolo(idArticoloA);

			StringBuffer testo = new StringBuffer("Scegli un articolo con cui fare scambio\n");
			Map<Integer, Articolo> articoliCategoria = articoli
					.getArticoliAttiviCategoriaNoFruitore(articoloSelezionato.getCategoria(), (Fruitore) utenteLogin);
			if (!articoliCategoria.isEmpty()) {
				ArrayList<Integer> chiavi = new ArrayList<Integer>();

				int i = 0;
				for (int chiave : articoliCategoria.keySet()) {
					testo.append((i + 1) + "->" + stampaArticolo(articoliCategoria.get(chiave)) + "\n");
					chiavi.add(chiave);
					i++;
				}

				testo.append("->");

				int indice = InputDati.leggiIntero(testo.toString(), 1, articoliCategoria.size());

				idArticoloB = chiavi.get(indice - 1);
			} else
				System.out.println("Non ci sono articoli con cui fare scambio");
		} else
			System.out.println("Non ci sono articoli da scambiare");

		return idArticoloB;
	}
	/**
	 * metodo che permette al fruitore di selezionare un appuntamento per lo scambio
	 * @param punto oggetto puntoDiInteresse 
	 * @param articoli listad egli articoli presenti nel sistema
	 * @param scambi lista degli scambi presenti nel sistema
	 * @return ritorna oggetto appuntamento selezionato dall'utente
	 */
	public Appuntamento getAppuntamento(PuntoInteresse punto, ListaArticoli articoli, ListaScambi scambi) {
		StringBuffer testo;
		int indice;
		Appuntamento appuntamento = null;

		if (punto != null) {
			System.out.println("Stai operando sulla piazza " + punto.getPiazza());

			testo = new StringBuffer("Scegli il luogo: \n");
			for (int i = 0; i < punto.getLuoghi().size(); i++)
				testo.append((i + 1) + "->" + (punto.getLuoghi()).get(i) + "\n");

			indice = InputDati.leggiIntero(testo.toString(), 1, punto.getLuoghi().size());
			String luogo = punto.getLuoghi().get(indice - 1);

			testo = new StringBuffer("Scegli il giorno: \n");
			for (int i = 0; i < punto.getGiorni().size(); i++)
				testo.append((i + 1) + "->" + punto.getGiorni().get(i) + "\n");

			indice = InputDati.leggiIntero(testo.toString(), 1, punto.getGiorni().size());
			Giorno giorno = punto.getGiorni().get(indice - 1);

			ArrayList<LocalTime> orariInserire = new ArrayList<LocalTime>();
			int j = 0;
			LocalTime ora, oraFine;

			testo = new StringBuffer("Scegli orario: \n");
			for (int i = 0; i < punto.getIntervalliOrari().size(); i++) {
				ora = punto.getIntervalliOrari().get(i).getOraInizio();
				oraFine = punto.getIntervalliOrari().get(i).getOraFine();

				do {
					orariInserire.add(ora);
					testo.append((j + 1) + "-> " + ora + "\n");
					ora = ora.plusMinutes(30);
					j++;
				} while (ora.getHour() < oraFine.getHour()
						|| ora.getHour() == oraFine.getHour() && ora.getMinute() < oraFine.getMinute()
						|| ora.getHour() == oraFine.getHour() && ora.getMinute() == oraFine.getMinute());
			}
			indice = InputDati.leggiIntero(testo.toString(), 1, orariInserire.size());
			ora = orariInserire.get(indice - 1);

			appuntamento = new Appuntamento(punto.getPiazza(), luogo, giorno, ora);
		} else
			System.out.println("Non ci sono punti di interesse");

		return appuntamento;
	}
	/**
	 * metodo che permette ad un fruitore di selezionare una proposta a cui vuole rispondere
	 * @param articoli lista degli articoli rpesenti nel sistema
	 * @param scambi lista degli scambi presenti nel sistema
	 * @param utenteLogin fruitore loggato
	 * @return ritorna l' oggetto scambio con cui il fruitore vuole interagire, null se non ci sono proposte disponibili
	 */
	public Scambio getProposta(ListaArticoli articoli, ListaScambi scambi, Utente utenteLogin) {
		Scambio proposta = null;
		List<Scambio> proposteRicevute = scambi.getProposte((Fruitore) utenteLogin, articoli);
		if (!proposteRicevute.isEmpty()) {
			StringBuffer testo = new StringBuffer(
					"Scegli una delle proposte a cui rispondere (0 per non rispondere a nessuno)\n");
			for (int i = 0; i < proposteRicevute.size(); i++)
				testo.append((i + 1) + "->" + stampaScambio(articoli, proposteRicevute.get(i)) + "\n");

			testo.append("->");

			int indice = InputDati.leggiIntero(testo.toString(), 0, proposteRicevute.size());
			if (indice != 0) {
				proposta = proposteRicevute.get(indice - 1);
			} else
				return null;
		} else
			System.out.println("Non ci sono proposte in arrivo");

		return proposta;
	}
	/**
	 * metodo che visualizza a video messaggio di password non coincidenti 
	 */
	public void stampaPasswordNonCoincidenti() {
		System.out.println("Non coincidono le password da te inserite");
	}
	/**
	 * metodo che stampa un messaggio di benvenuto a video
	 * @param username username del fruitore loggato
	 */
	public void stampaBenvenuto(String username) {
		System.out.printf(MESSAGGIO_BENVENUTO, username);
	}
	/**
	 * metodo che visualizza a video messaggio di login non andato a buon fine
	 */
	public void stampaLoginNonRiuscito() {
		System.out.println("Username o password errata");
	}
	/**
	 * metodo che visualizza a video il menu per un configuratore
	 * @return ritorna un intero indicante la scelta effettuata dal configuratore 
	 */
	public int menuConfiguratore() {
		MyMenu menu = new MyMenu(TITOLO_MENU, VOCI_MENU_CONFIGURATORE);
		return menu.scegli();
	}
	/**
	 * metodo che visualizza a video il menu per un fruitore
	 * @return ritorna un'intero indicante la scelta selezionata dal fruitore
	 */
	public int menuFruitore() {
		MyMenu menu = new MyMenu(TITOLO_MENU, VOCI_MENU_FRUITORE);
		return menu.scegli();
	}
	/**
	 * metodo che visualizza a video il menu delle proposte  
	 * @return intero indicante la scelta effettuata dall'utente 
	 */
	public int menuProposte() {
		MyMenu menu = new MyMenu(TITOLO_MENU_PROPOSTA, VOCI_MENU_PROPOSTA);
		return menu.scegli();
	}
	/**
	 * metodo che visualizza a video il menu di login 
	 * @return ritorna la scelta effettuata 
	 */
	public int menuLogin() {
		MyMenu menu = new MyMenu(TITOLO_MENU_LOGIN, VOCI_MENU_LOGIN);
		return menu.scegli();
	}
	/**
	 * metodo che visualizza a video messaggio di salvataggio su file riuscito
	 */
	public void stampaSalvataggioRiuscito() {
		System.out.println("Salvataggio riuscito");
	}
	/**
	 * metodo che visualizza a video messaggio di salvataggio su file non riuscito
	 */
	public void stampaSalvataggioNonRiuscito() {
		System.out.println("Salvataggio non riuscito");
	}
	/**
	 * metodo che visualizza a video messaggio di offerte non presenti per una determinata categoria 
	 */
	public void stampaNoOfferteCategoria() {
		System.out.println("Questa categoria non ha offerte");
	}
	/**
	 * metodo che visualizza a video messaggio relativo ad un errore generico
	 */
	public static void stampaErroreGenerico() {
		System.out.println(ERRORE_GENERICO);
	}
	/**
	 * metodo che visualizza a video tutte le categorie radice presenti nel sistema
	 * @param categorie lista delle categorie presenti nel sistema
	 */
	public void stampaCategorieRadice(ListaCategorie categorie) {
		System.out.println(stampaListaCategoria(categorie));
	}
	/**
	 * metodo che visualizza a video i luoghi presenti nel sistema 
	 * @param punto oggetto punto di interesse 
	 */
	public void stampaLuoghi(PuntoInteresse punto) {
		if (punto == null)
			System.out.println("Punto non presente");
		else
			System.out.println(stampaPuntoInteresse(punto));
	}
	/**
	 * metodo che visualiza a video gli articoli attivi presenti per una specifica categoria
	 * @param categorie lista di categorie presenti nel sistema
	 * @param articoli lista di articoli presenti nel sistema
	 */
	public void stampaArticoliAttiviCategoria(ListaCategorie categorie, ListaArticoli articoli) {
		Categoria categoria = getCategoria(categorie);
		Map<Integer, Articolo> articoliCategoria = articoli.getArticoliAttivi(categoria);
		if (!articoliCategoria.isEmpty()) {
			System.out.println("Articoli attivi della categoria " + categoria.getNome());
			for (Articolo a : articoliCategoria.values())
				System.out.println(stampaArticolo(a));
		} else
			System.out.println("Non ci sono articoli attivi della categoria " + categoria.getNome());
	}
	/**
	 * metodo che visualizza a video gli articoli inseriti da uno specifico fruitore 
	 * @param articoli lista degli articoli rpesenti nel sistema
	 * @param utenteLogin utente loggato
	 */
	public void stampaArticoliFruitore(ListaArticoli articoli, Utente utenteLogin) {
		Map<Integer, Articolo> articoliFruitore = articoli.getArticoliFruitore((Fruitore) utenteLogin);
		if (!articoliFruitore.isEmpty()) {
			System.out.println("Articoli di " + utenteLogin.getUsername().getUsername());

			for (Articolo a : articoliFruitore.values()) {
				System.out.println(stampaArticolo(a));
			}
		} else
			System.out.println("Non ci sono articoli");
	}
	/**
	 * metodo che visualizza a video le offerte relative a uno specifico fruitore
	 * @param articoli lista degli articoli pesenti nel sistema
	 * @param scambi lista di scambi presenti nel sistema
	 * @param utenteLogin utente loggato
	 */
	public void stampaOfferteFruitore(ListaArticoli articoli, ListaScambi scambi, Utente utenteLogin) {
		Collection<Scambio> offerteFruitore = scambi.getOfferteFruitore((Fruitore) utenteLogin, articoli);

		if (!offerteFruitore.isEmpty()) {
			for (Scambio s : offerteFruitore) {
				System.out.println(stampaScambio(articoli, s));
				if (articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().toString()
						.equals(ArticoloState.CHIUSA))
					System.out.println("Stato: Offerta chiusa");
				else {
					if (s.getToken().equals(Token.A))
						System.out.println("Stato: Ora tocca a te rispondere");
					else
						System.out.println("Stato: In attesa di una risposta dell'altro fruitore");
				}
				System.out.println("");
			}
		} else
			System.out.println("Non ci sono offerte");
	}
	/**
	 * metodo che visualizza a video messaggio di risposta per una proposta on effettuata
	 */
	public void stampaNoRispostaProposta() {
		System.out.println("Non hai risposto a questa proposta");
	}
	/**
	 * metodo che visualizza a video messaggio di arrivederci
	 */
	public void stampaArrivederci() {
		System.out.println("Arrivederci");
	}
	/**
	 * metodo che visualizza a video messaggio di invalidita per una voce di menu 
	 */
	public void stampaVoceMenuNonValida() {
		System.out.println("Voce di menù non valida");
	}
	/**
	 * metodo che visualizza a video messaggio di aggiunta per un articolo eseguita correttamente
	 */
	public void stampaArticoloAggiunto() {
		System.out.println("Articolo aggiunto correttamente");
	}
	/**
	 * metodo che visualizza a video messaggio di aggiunta per un articolo non eseguita correttamente 
	 */
	public void stampaArticoloNonAggiunto() {
		System.out.println("Articolo non aggiunto");
	}
	/**
	 * metodo che visualizza a video un messaggio di errore
	 * @param messaggio stringa da visualizzare 
	 */
	public void stampaErrore(String messaggio) {
		System.out.println(messaggio);
	}
//metodi per la visualizzazione a video di messaggi di errore 
	public static void stampaErroreEstensione() {
		System.out.println(ERRORE_ESTENSIONE);
	}

	public static void stampaErroreChiusuraFile() {
		System.out.println(ERRORE_CHIUSURA_FILE);
	}

	public static void stampaErroreClasse() {
		System.out.println(ERRORE_CLASSE);
	}

	public static void stampaErroreLettura() {
		System.out.println(ERRORE_LETTURA);
	}

	public static void stampaErroreFileNonTrovato(File fileCaricare) {
		System.out.printf(ERRORE_FILE_NON_TROVATO, fileCaricare.getName());
	}

	public static void stampaErroreScrittura() {
		System.out.println(ERRORE_SCRITTURA);
	}

	public void stampaRegistrazioneSuccesso() {
		System.out.println("Registrazione avvenuta con successo");
	}

	public void stampaUtenteScelto() {
		System.out.println("Nome utente già scelto");
	}

	public void stampaPrimoAccesso() {
		System.out.println("Primo accesso devi cambiare password");
	}

	public void stampaArticoliAttiviNonPresenti() {
		System.out.println("Nessun articolo attivo trovato");
	}

// metodi toString relativi a oggetti CAMPO , CATEGORIA, ECC
	private String stampaCampo(Campo campo) {
		StringBuilder testo = new StringBuilder();
		testo.append(campo.getNome() + " : " + campo.getTipo().toString() + ", Obbligatorio:");
		if (campo.isObbligatorio())
			testo.append("Sì");
		else
			testo.append("No");
		return testo.toString();
	}

	private String stampaCategoria(Categoria categoria) {
		StringBuilder testo = new StringBuilder();
		testo.append("Nome: " + categoria.getNome() + "\n");
		testo.append("Descrizione: " + categoria.getDescrizione() + "\n");
		testo.append("Campi:");
		if (!categoria.getCampi().isEmpty()) {
			for(Campo c: categoria.getCampi())
				testo.append(stampaCampo(c) + "\n");
		}else
			testo.append("Non sono presenti campi\n");
		if (categoria.getPadre() == null)
			testo.append("Padre: Radice\n");
		else
			testo.append("Padre:" + categoria.getPadre().getNome() + "\n");

		return testo.toString();
	}

	/**
	 * Metodo che raccoglie tutte le categorie RADICE (cioe quelle che non hanno una
	 * categoria padre)
	 * 
	 * @return ritorna una stringa contenente tutte le categorie radice
	 * 
	 * @precondition
	 * @postcondition testo!=null && listaCategorie.size()==listaCategorie'.size()
	 *                && per ogni i intero, 0<i<listaCategorie.size(),
	 *                listaCategorie.get(i)==listaCategorie'.get(i)
	 */
	private String visualizzaCategorieRadice(ListaCategorie listaCategorie) {
		StringBuilder testo = new StringBuilder();
		int cont = 0;

		for (Categoria c : listaCategorie.getListaCategorie()) {
			if (c.getPadre() == null) {
				testo.append(stampaCategoria(c) + "\n");
				cont++;
			}
		}
		if (cont == 0)
			return "Non ci sono categorie radice";
		else
			return testo.toString();
	}

	/**
	 * Metodo che ritorna la lista delle sottoCategorie (escluse quindi le categorie radice)
	 * @return String contenente tutte le sottoCategorie presenti nella lista
	 */
	private String visualizzaSottoCategorie(ListaCategorie listaCategorie) {
		StringBuilder testo = new StringBuilder();
		int cont = 0;

		for (Categoria c : listaCategorie.getListaCategorie()) {
			if (c.getPadre() != null) {
				testo.append(stampaCategoria(c) + "\n");
				cont++;
			}
		}

		if (cont == 0)
			return "Non ci sono sottocategorie";
		else
			return testo.toString();
	}
	/**
	 * Metodo che preleva la lista di categorie presenti nel sistema
	 * @param listaCategorie lista di oggetti Categoria presenti nel sistema
	 * @return ritorna stringa contenete tutte le categorie  
	 */
	private String stampaListaCategoria(ListaCategorie listaCategorie) {
		if (!listaCategorie.getListaCategorie().isEmpty())
			return visualizzaCategorieRadice(listaCategorie) + visualizzaSottoCategorie(listaCategorie);
		else
			return "Non sono presenti categorie";
	}
	/**
	 * metodo che preleva le informazioni relative ad un appuntamento
	 * @param appuntamento oggetto appuntamento di cui si voglio visualizzare le informazioni
	 * @return ritorna una stringa con le informazioni relative all'appuntamento
	 */
	private String stampaAppuntamento(Appuntamento appuntamento) {
		StringBuffer testo = new StringBuffer("Appuntamento\n");
		testo.append("Piazza: " + appuntamento.getPiazza() + "\n");
		testo.append("Luogo: " + appuntamento.getLuogo() + "\n");
		testo.append("Giorno: " + appuntamento.getGiorno() + "\n");
		testo.append("Orario: " + appuntamento.getOrario().toString());

		return testo.toString();
	}
	/**
	 * metodo che preleva le informazioni relative ad un intervallo orario per un appuntamento
	 * @param intervalloOrario intervallo oriario di cui si vogliono le informazioni
	 * @return stringa contenente le informazioni per un intervallo orario
	 */
	private String stampaIntervalloOrario(IntervalloOrario intervalloOrario) {
		return intervalloOrario.getOraInizio().getHour() + "." + intervalloOrario.getOraInizio().getMinute() + " - "
				+ intervalloOrario.getOraFine().getHour() + "." + intervalloOrario.getOraFine().getMinute();
	}
	/**
	 * metodo che preleva le informazioni per un punto di interesse
	 * @param punto punto di interesse di cui si vogliono tutte le info
	 * @return stringa contenete le infromazioni relative al punto di interesse
	 */
	private String stampaPuntoInteresse(PuntoInteresse punto) {
		StringBuffer testo = new StringBuffer();
	    
	    testo.append("Piazza: " + punto.getPiazza() + "\n");
	    testo.append("Luoghi:\n");
	    if(!punto.getLuoghi().isEmpty()) {
		    for(String luogo: punto.getLuoghi())
		      testo.append("\t-> " + luogo + "\n");
	    }
	    else
	    	testo.append("Non ci sono luoghi\n");
	    
	    testo.append("\n");
	    testo.append("Giorni:\n");
	    
	    if(!punto.getGiorni().isEmpty()) {
		    for(Giorno giorno: punto.getGiorni())
		      testo.append("\t-> " + giorno.toString() + "\n");
	    }
	    else
	    	testo.append("Non ci sono giorni inseriti\n");
	    testo.append("Intervalli: \n");
	    
	    if(!punto.getIntervalliOrari().isEmpty()) {
		    for(IntervalloOrario intervallo: punto.getIntervalliOrari())
		    	testo.append("\t-> " + stampaIntervalloOrario(intervallo) + "\n");
	    }
	    else
	    	testo.append("Non ci sono intervalli orari inseriti\n");
	    
	    testo.append("Scadenza: "+punto.getScadenza());
	    
	    return testo.toString();
	}
	
	/**
	 * Metodo che preleva i dati relativi agli articoli in scambio  
	 * @param articoli lista degli articoli presenti nel sistema
	 * @return ritorna una stringa contenente i dati degli articoli in scambio ( nomi ) 
	 */
	public String stampaScambio(ListaArticoli articoli, Scambio scambio) {
		StringBuffer testo=new StringBuffer();
		testo.append(articoli.getArticolo(scambio.getIdArticoloA()).getNome()+" -> "+articoli.getArticolo(scambio.getIdArticoloB()).getNome()+"\n");
		
		if(scambio.getAppuntamento()!=null)
			testo.append(stampaAppuntamento(scambio.getAppuntamento()));
		
		return testo.toString();
	}
	/**
	 * metodo che acquisisce le informazioni relative ad un articolo
	 * @param articolo oggetto di cui si vogliono le info 
	 * @return stringa contenente le informazioni relative ad un articolo
	 */
	private String stampaArticolo(Articolo articolo) {
		StringBuffer testo=new StringBuffer();
		testo.append("Nome: "+articolo.getNome()+"\n");
		testo.append("Categoria: "+articolo.getCategoria().getNome()+"\n");
		testo.append("Campi\n");
		if(!articolo.getCampi().isEmpty()) {
			for(CampoCompilabile c:articolo.getCampi())
				testo.append(stampaCampoCompilabile(c)+"\n");
		}else
			testo.append("Non sono presenti campi compilabili\n");
		
		testo.append("Stato: "+articolo.statoOffertaAttuale()+"\n");
		return testo.toString();
	}
	/**
	 * metodo che acquisisce le informazioni relative ad un campo compilabile
	 * @param campoCompilabile campo di cui si vogliono le informazioni
	 * @return stringa contenente le informazioni relative ad un campo compilabile
	 */
	private String stampaCampoCompilabile(CampoCompilabile campoCompilabile) {
		if(campoCompilabile.getValoreCampo()==null)
			return campoCompilabile.getNomeCampo()+" : Nessun valore inserito";
		else 
			return campoCompilabile.getNomeCampo()+" : "+ campoCompilabile.getValoreCampo().getValore();
	}
	/**
	 * metodo che acquisisce la informazioni relative agli articoli presenti nel sistema
	 * @param articoli lista di articoli pesenti nel sistema
	 * @return stringa contenente le informazioni sugli articoli presenti nel sistema
	 */
	private String stampaListaArticoli(ListaArticoli articoli) {
		if(!articoli.getLista().isEmpty()) {
			StringBuilder testo=new StringBuilder("Lista articoli");
			for(Articolo a:articoli.getLista().values()){
				testo.append(stampaArticolo(a)+"\n");
			}
			
			return testo.toString();
		}else
			return "Non sono presenti articoli";
	}
	/**
	 * metodo che acquisisce le informazioni relative agli utenti registrati nel sistema
	 * @param utenti lista di utenti egistrati nel sistema
	 * @return stringa contenente le informazioni sugli utenti
	 */
	private String stampaListaUtenti(ListaUtenti utenti) {
		if(!utenti.getUtenti().isEmpty()) {
			String testo="Lista utenti\n";
			for(Utente u:utenti.getUtenti())
				testo+=u.getUsername().getUsername()+"\n";
			return testo;
		}else
			return "Non sono presenti utenti";
	}
}
