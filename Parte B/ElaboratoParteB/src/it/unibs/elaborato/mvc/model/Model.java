package it.unibs.elaborato.mvc.model;

import java.util.Collection;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.chainofresponsability.ConfiguratoreRenderer;
import it.unibs.elaborato.chainofresponsability.DefaultRenderer;
import it.unibs.elaborato.chainofresponsability.FruitoreRenderer;
import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.mercato.Appuntamento;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.Scadenza;
import it.unibs.elaborato.mercato.Scambio;
import it.unibs.elaborato.mercato.Token;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.utente.Configuratore;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.ListaUtenti;
import it.unibs.elaborato.utente.Password;
import it.unibs.elaborato.utente.Username;
import it.unibs.elaborato.utente.Utente;
import it.unibs.elaborato.utils.FileUtils;
import it.unibs.elaborato.utils.XMLUtils;
/**
 * Classe MODEL (del modello mvc) che rappresenta i dati e il comportamento dell'applicazione (regole di bussines)
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class Model {
	private PuntoInteresse punto;
	private ListaUtenti utenti;
	private ListaCategorie categorie;
	private ListaArticoli articoli;
	private ListaScambi scambi;

	// costanti per i file
	private static final String PERCORSO_DATI = "src\\it\\unibs\\elaborato\\dati";
	private static final String NOME_FILE_UTENTI = "Utenti.dat";
	private static final String NOME_FILE_CATEGORIE = "Categorie.xml";
	private static final String NOME_FILE_PUNTIINTERESSE = "PuntiInteresse.xml";
	private static final String NOME_FILE_ARTICOLI = "Articoli.dat";
	private static final String NOME_FILE_SCAMBI = "Scambi.dat";

	// costanti menù proposte
	private static final String TITOLO_MENU_PROPOSTA = "Scegli";
	private static final String[] VOCI_MENU_PROPOSTA = { "Conferma la proposta", "Scegli un nuovo appuntamento" };
	private static final int CONFERMA_PROPOSTA = 1;
	private static final int SCEGLI_NUOVO_APPUNTAMENTO = 2;
	
	/**
	 * metodo costruttore della classe model che inizializza gli attributi a null 
	 */
	public Model() {
		super();
		this.punto = null;
		this.utenti = null;
		this.categorie = null;
		this.articoli = null;
		this.scambi = null;
	}

	/**
	 * Metodo che verifica se si tratta del primo accesso al sistema da parte del
	 * configuratore
	 * 
	 * @param u utente (configuratore) che vuole accedere
	 * @return ritorna TRUE se si tratta del primo accesso da parte del
	 *         configuratore
	 * @precondition u != null
	 * @postcondition valore_ritorno == true || valore_ritorno == false
	 */
	public boolean isPrimoAccesso(Utente u) {
		if (u instanceof Configuratore && u.getPassword().getPassword().equals(Configuratore.PASSWORD_DEFAULT))
			return true;
		return false;
	}
	/**
	 * metodo che effettua il controllo sul login
	 * @param username nome utente
	 * @param password password inserita dall'utente
	 * @return
	 */
	public Utente controllaLogin(String username, String password) {
		return utenti.loginUtente(username, password);
	}
	/**
	 * metodo che effettua la registrazione i un nuovo utente
	 */
	public boolean registrazione(String username, String nuovaPassword, String nuovaPasswordConferma) throws UsernameException, PasswordException {
		Utente nuovoUtente = new Fruitore(new Username(username), new Password(nuovaPassword));
		return utenti.aggiungi(nuovoUtente);
	}
	/**
	 * metodo che permette di cambiare la password a un utente
	 * @param username 
	 * @param nuovaPassword
	 * @param nuovaPasswordConferma
	 * @return valore di stato del cambio password 
	 * @throws PasswordException in czo di errore viene lanciata la relativa eccezione
	 */
	public boolean cambiaPassword(Username username, String nuovaPassword, String nuovaPasswordConferma)
			throws PasswordException {
		if (nuovaPassword.equals(nuovaPasswordConferma)) {
			utenti.cambioPassword(username.getUsername(), nuovaPassword);
			return true;
		}
		return false;
	}
	/**
	 * metodo che permette di scegliere la tipologia di utente 
	 * @param utenteLogin
	 * @return ritorna il tipo di utente 
	 */
	public int scegliMenuUtente(Utente utenteLogin) {
		// scelgo tra configuratore e fruitore
		return new FruitoreRenderer(new ConfiguratoreRenderer(new DefaultRenderer())).render(utenteLogin);
	}
	/**
	 * metodo che ricava le offere relative a una categoria scelta
	 * @param categoriaScelta categoria selezionata
	 * @return ritorna le offerte o null se non presenti 
	 */
	public Collection<Scambio> offerteCategoria(Categoria categoriaScelta) {
		if (categoriaScelta != null) {
			Collection<Scambio> offerte = scambi.getOfferteCategoria(categoriaScelta, articoli);
			return offerte;
		}
		return null;
	}
	/**
	 * metodo che permette di inserire un nuovo articolo ad un utente fruitore 
	 * @param articoloInserito articolo da inserire 
	 * @return ritorna true se articolo inserito correttamente, false altrimenti
	 */
	public boolean inserisciArticoloFruitore(Articolo articoloInserito) {
		if (articoloInserito != null) {
			articoli.aggiungi(articoloInserito);
			return true;
		}
		return false;
	}
	/**
	 * metodo che permette di ritirare un' offerta
	 * @param id identificativo dell'offerta da ritirare
	 */
	public void ritiraOfferta(int id) {
		articoli.getArticolo(id).ritirata();
	}
	/**
	 * metodo che permette di effettuare una nuova proposta
	 * @param idArticoloA 
	 * @param idArticoloB
	 */
	public void effettuaProposta(int idArticoloA, int idArticoloB) {
		articoli.getArticolo(idArticoloA).accoppiata();
		articoli.getArticolo(idArticoloB).selezionata();
		scambi.aggiungi(new Scambio(idArticoloA, idArticoloB));
	}
	/** ????
	 * metodo che permette ad un'utente di rispondere ad una proposta
	 * @param proposta oggetto proposta a cui si vuole rispondere
	 * @param propostaCopia oggetto copia della proposta originale a cui si vuole rispondere
	 * @param appuntamento
	 */
	public void risposta(Scambio proposta, Scambio propostaCopia, Appuntamento appuntamento) {
		Token token;
		if (proposta.getToken().equals(Token.A))
			token = Token.B;
		else
			token = Token.A;
		proposta.risposta(articoli, appuntamento, token);
		scambi.elimina(propostaCopia);
		scambi.aggiungi(proposta);
	}
	/**
	 * metodo che esegue il salvataggio dei dati su file 
	 * @return true se il salvataggio èandato a buon fine, false altrimenti
	 */
	public boolean salva() {
		boolean salvatoUtenti = FileUtils.salvaFile(utenti, PERCORSO_DATI, NOME_FILE_UTENTI);
		boolean salvatoArticoli = FileUtils.salvaFile(articoli, PERCORSO_DATI, NOME_FILE_ARTICOLI);
		boolean salvatoScambi = FileUtils.salvaFile(scambi, PERCORSO_DATI, NOME_FILE_SCAMBI);
		
		if (salvatoUtenti && salvatoArticoli && salvatoScambi)
			return true;
		return false;
	}
	/**
	 * metodo che carica gli articoli da file
	 */
	public void caricaArticoli() {
		articoli = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_ARTICOLI);
		if (articoli == null)
			articoli = new ListaArticoli();
	}
	/**
	 * metodo che carica i punti di interesse da file
	 */
	public void caricaPuntiInteresse() {
		punto = XMLUtils.leggiPuntiInteresse(PERCORSO_DATI, NOME_FILE_PUNTIINTERESSE);
	}
	/**
	 * metodo che carica le categorie da file
	 */
	public void caricaCategorie() {
		categorie = XMLUtils.leggiCategorie(PERCORSO_DATI, NOME_FILE_CATEGORIE);
	}
	/**
	 * metodo che carica gli scambi da file
	 */
	public void caricaScambi() {
		scambi = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_SCAMBI);
		if (scambi == null)
			scambi = new ListaScambi();
	}
	/**
	 * metodo che carica gli utenti da file
	 */
	public void caricaUtenti() throws PasswordException, UsernameException {
		utenti = FileUtils.caricaFile(PERCORSO_DATI, NOME_FILE_UTENTI);
		if (utenti == null)
			utenti = new ListaUtenti();
	}
	/**
	 * metodo che richiama tutti i caricamenti dei dati da file 
	 */
	public void carica() {
		caricaCategorie();
		caricaPuntiInteresse();
		caricaArticoli();
		caricaScambi();
	}
	/**
	 * metodo che controlla le scadenze delle proposte
	 */
	public void controllaScadenze() {
		Scadenza.controllaScadenza(getScambi(), getPunto(), getArticoli());
	}
//metodi getter vari
	public PuntoInteresse getPunto() {
		return punto;
	}

	public ListaUtenti getUtenti() {
		return utenti;
	}

	public ListaCategorie getCategorie() {
		return categorie;
	}

	public ListaArticoli getArticoli() {
		return articoli;
	}

	public ListaScambi getScambi() {
		return scambi;
	}
}
