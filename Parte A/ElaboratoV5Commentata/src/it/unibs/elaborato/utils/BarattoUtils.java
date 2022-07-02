package it.unibs.elaborato.utils;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.utente.Configuratore;
import it.unibs.elaborato.utente.ListaUtenti;
import it.unibs.elaborato.utente.Utente;
/**Classe di utilità che permette di effettuare operazioni sul sistema Baratto 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 * 
 */
public class BarattoUtils {

	// costanti per i file
	private static final String PERCORSO_DATI = "src\\it\\unibs\\elaborato\\dati";
	private static final String NOME_FILE_UTENTI = "Utenti.dat";
	private static final String NOME_FILE_CATEGORIE = "Categorie.xml";
	private static final String NOME_FILE_PUNTIINTERESSE = "PuntiInteresse.xml";
	private static final String NOME_FILE_ARTICOLI = "Articoli.dat";
	private static final String NOME_FILE_SCAMBI = "Scambi.dat";

	/**
	 * Metodo che verifica se si tratta del primo accesso al sistema da parte del configuratore
	 * @param u utente (configuratore) che vuole accedere
	 * @return ritorna TRUE se si tratta del primo accesso da parte del configuratore 
	 * @precondition u != null
	 * @postcondition valore_ritorno == true || valore_ritorno == false
	 */
	public static boolean isPrimoAccesso(Utente u) {
		if (u instanceof Configuratore && u.getPassword().equals(Configuratore.PASSWORD_DEFAULT))
			return true;
		return false;
	}

	/**
	 * Metodo che salva su file le strutture dati che compongono il sistema
	 * @param utenti lista degli utenti iscritti
	 * @param articoli lista degli articoli inseriti
	 * @param scambi lista degli scambi egìffettuati o in corso
	 */
	public static void salva(ListaUtenti utenti, ListaArticoli articoli, ListaScambi scambi) {
		boolean salvatoUtenti = FileUtils.salvaFile(utenti, PERCORSO_DATI, NOME_FILE_UTENTI);
		boolean salvatoArticoli = FileUtils.salvaFile(articoli, PERCORSO_DATI, NOME_FILE_ARTICOLI);
		boolean salvatoScambi = FileUtils.salvaFile(scambi, PERCORSO_DATI, NOME_FILE_SCAMBI);

		if (salvatoUtenti && salvatoArticoli && salvatoScambi)
			System.out.println("Salvataggio riuscito");
		else
			System.out.println("Salvataggio non riuscito");
	}
	
	/**
	 * Metodo che controlla le scadenze di tutte gli scambi in corso
	 * @param punto lista dei punti di interesse inseriti
	 * @param articoli lista degli articoli presenti nel sistema
	 * @param scambi lista degli scambi 
	 */
	public static void controllaScadenze(PuntoInteresse punto, ListaArticoli articoli,
			ListaScambi scambi) {
		try {
			scambi.controllaScadenza(punto, articoli);
		} catch (IllegalStateException e) {
			
		}
	}
	
	/**
	 * Metodo che carica la lista degli scambi da file
	 * @return ritorna una lista di scambi
	 * @postcondition scambi != null
	 */
	public static ListaScambi caricaScambi() {
		ListaScambi scambi;
		scambi = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_SCAMBI);
		if (scambi == null)
			scambi = new ListaScambi();
		return scambi;
	}
	
	/**
	 * Metodo che carica la lista degli articoli da file
	 * @return ritorna una lista di articoli
	 * @postcondition articoli != null
	 */
	public static ListaArticoli caricaArticoli() {
		ListaArticoli articoli;
		articoli = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_ARTICOLI);
		if (articoli == null)
			articoli = new ListaArticoli();
		return articoli;
	}
	
	/**
	 * Metodo che carica la lisa dei punti di interesse da file
	 * @return ritorna una lista di punti di interesse
	 */
	public static PuntoInteresse caricaPuntiInteresse() {
		PuntoInteresse punto;
		punto = XMLUtils.leggiPuntiInteresse(PERCORSO_DATI, NOME_FILE_PUNTIINTERESSE);
		return punto;
	}

	/**
	 * Metodo che carica la lista delle categorie da file
	 * @return ritorna una lista di categorie
	 */
	public static ListaCategorie caricaCategorie() {
		ListaCategorie categorie;
		categorie = XMLUtils.leggiCategorie(PERCORSO_DATI, NOME_FILE_CATEGORIE);
		return categorie;
	}
	
	/**
	 * Metodo che carica la lista degli utenti da file
	 * @return ritorna una lista di utenti
	 * @postcondition utenti != null
	 */
	public static ListaUtenti caricaUtenti() {
		ListaUtenti utenti;
		utenti = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_UTENTI);
		if (utenti == null)
			utenti = new ListaUtenti();
		return utenti;
	}
	
	/**
	 * metodo che visualizza a video una lista di punti di interesse 
	 * @param punto lista dei punti di interesse da visualizzare
	 */
	public static void visualizzaLuoghi(PuntoInteresse punto) {
		if(punto==null) 
			System.out.println("Punto non presente!");
		else
			System.out.println(punto.toString());
	}

	
	/**
	 * Metodo che visualizza gli articoli relativi ad una specifica categoria
	 * @param categorie lista delle categorie presenti nel sistema
	 * @param articoli lista di articoli 
	 */
	public static void visualizzaArticoliAttiviCategoria(ListaCategorie categorie, ListaArticoli articoli) {
		Categoria categoria = scegliCategoria(categorie);
		HashMap<Integer, Articolo> articoliCategoria = articoli.getArticoliAttivi(categoria);
		if(!articoliCategoria.isEmpty()) {
			System.out.println("Articoli attivi della categoria " + categoria.getNome());
			for (Articolo a : articoliCategoria.values())
				System.out.println(a.toString());
		}else
			System.out.println("Non ci sono articoli attivi della categoria " + categoria.getNome());
	}
	
	
	/**
	 * Metodo che permette di selezionare una categoria
	 * @param categorie lista delle categorie 
	 * @return ritorna un oggetto Categoria se è stata trovata, NULL altrimenti
	 */
	public static Categoria scegliCategoria(ListaCategorie categorie) {
		ArrayList<Categoria> categorieFoglie = categorie.getCategorieFoglie();
		Categoria categoria=null;
		if(!categorieFoglie.isEmpty()) {
			StringBuffer testo = new StringBuffer("Scegli una categoria\n");
			for (int i = 0; i < categorieFoglie.size(); i++)
				testo.append((i + 1) + "-> " + categorieFoglie.get(i).getNome() + "\n");
			int indiceScelta = InputDati.leggiIntero(testo.toString(), 1, categorieFoglie.size());
			categoria= categorieFoglie.get(indiceScelta - 1);
		}else
			System.out.println("Non ci sono categorie foglie");
		
		return categoria;
	}
}
