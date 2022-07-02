package it.unibs.elaborato.mvc.controller;

import java.util.Collection;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.mercato.Scambio;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mvc.model.Model;
import it.unibs.elaborato.mvc.view.View;
import it.unibs.elaborato.utente.Utente;
/**
 * Classe CONTROLLER (modello mvc) che trasforma le interazioni dell'utente sulla classe VIEW in richieste alla classe MODEL
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class Controller {
	private Model model;
	private View view;

	// costanti menù configuratore
	private static final int VISUALIZZA_CATEGORIA_CONFIGURATORE = 1;
	private static final int VISUALIZZA_LUOGHI_CONFIGURATORE = 2;
	private static final int VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_CONFIGURATORE = 3;
	private static final int VISUALIZZA_OFFERTE_IN_SCAMBIO_CHIUSE_CONFIGURATORE = 4;

	// costanti menù fruitore
	private static final int VISUALIZZA_LUOGHI_FRUITORE = 1;
	private static final int VISUALIZZA_CATEGORIA_FRUITORE = 2;
	private static final int INSERISCI_ARTICOLO_FRUITORE = 3;
	private static final int RITIRA_OFFERTA_FRUITORE = 4;
	private static final int VISUALIZZA_ARTICOLI_FRUITORE = 5;
	private static final int VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_FRUITORE = 6;
	private static final int EFFETTUA_PROPOSTA_FRUITORE = 7;
	private static final int VISUALIZZA_PROPOSTE_RICEVUTE_FRUITORE = 8;
	private static final int VISUALIZZA_OFFERTE_FRUITORE = 9;
	private static final int ESCI = 0;

	// costanti menù proposte
	private static final int CONFERMA_PROPOSTA = 1;
	private static final int SCEGLI_NUOVO_APPUNTAMENTO = 2;

	// costanti menù login
	private static final int LOGIN = 1;
	private static final int REGISTRAZIONE = 2;
	
	/**
	 * metodo costruttore che associa alla classe controller i rispettivi oggetti VIEW e MODEL
	 * @param model
	 * @param view
	 */
	public Controller(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
	}
	
	/**
	 * Metodo che gestisce l'interazione LOGIN passando le credenziali acquisite dalla classe VIEW al CONTROLLER
	 * @throws PasswordException in caso di password errata lancia la rispettiva eccezione 
	 */
	public void login() throws PasswordException {
		boolean login = false;
		Utente utenteLogin = null;

		do {
			utenteLogin = model.controllaLogin(view.getUsername(), view.getPassword());
			if (utenteLogin != null) {
				if (model.isPrimoAccesso(utenteLogin)) {
					boolean passwordCambiata;
					do {
						view.stampaPrimoAccesso();
						passwordCambiata = model.cambiaPassword(utenteLogin.getUsername(), view.getPassword(),
								view.getConfermaPassword());
						if (passwordCambiata) {
							view.stampaBenvenuto(utenteLogin.getUsername().getUsername());
							login = true;
						} else
							view.stampaPasswordNonCoincidenti();
					} while (!passwordCambiata);
				}else {
					view.stampaBenvenuto(utenteLogin.getUsername().getUsername());
					login = true;
				}
			} else
				view.stampaLoginNonRiuscito();
		} while (!login);

		sceltaMenu(utenteLogin);

	}

	/**metodo per scegliere se invocare un menù di un configuratore o di un fruitore
	 * @param utenteLogin utente loggato nel sistema in quel momento
	 */
	public void sceltaMenu(Utente utenteLogin) {
		int risposta;
		switch (model.scegliMenuUtente(utenteLogin)) {
		case 1:
			do {
				risposta = view.menuConfiguratore();
				menuConfiguratore(risposta);
			} while (risposta != ESCI);

			break;
		case 2:
			do {
				risposta = view.menuFruitore();
				menuFruitore(utenteLogin, risposta);
			} while (risposta != ESCI);
			break;
		}
	}
	
	/**
	 * metodo che richiede al model tutte le offerte relative a una categoria selezionata dall'utente e le passa alla VIEW 
	 */
	public void offerteCategorie() {
		Collection<Scambio> offerte = model.offerteCategoria(scegliCategoria());
		if (offerte != null) {
			if (!offerte.isEmpty()) {
				for (Scambio s : offerte)
					view.stampaScambio(model.getArticoli(), s);
			} else
				view.stampaNoOfferteCategoria();
		} else
			View.stampaErroreGenerico();
	}
	
	/**
	 * metodo che acquisisce dal model la lista di categorie disponibili e la passa alla VIEW (che poi interrogherà l'utente) 
	 * @return ritorna la categoria selezionata dall' utente
	 */
	public Categoria scegliCategoria() {
		return view.getCategoria(model.getCategorie());
	}
	
	/**
	 * metodo che fa visualizzare alla view l'esito del salvataggio
	 */
	public void salva() {
		if (model.salva())
			view.stampaSalvataggioRiuscito();
		else
			view.stampaSalvataggioNonRiuscito();
	}
	/**
	 * metodo che fa visualizzare alla view l'esito del caricamento da file 
	 */
	public void carica() {
		try {
			model.caricaUtenti();
		} catch (PasswordException e) {
			view.stampaErrore(e.getMessage());
		} catch (UsernameException e) {
			view.stampaErrore(e.getMessage());
		}
		model.carica();
	}
	/**
	 * metodo che fa visualizzare alla view tutte le categorie radice
	 */
	public void visualizzaCategorieRadice() {
		view.stampaCategorieRadice(model.getCategorie());
	}
	/**
	 * metodo che richiama i metodi della view per visualizzare l' output delle voci del menu del CONFIGURTORE
	 * @param risposta voce di menu selezionata dal configuratore
	 */
	public void menuConfiguratore(int risposta) {
		switch (risposta) {
		case VISUALIZZA_CATEGORIA_CONFIGURATORE:
			visualizzaCategorieRadice();
			break;
		case VISUALIZZA_LUOGHI_CONFIGURATORE:
			visualizzaLuoghi();
			break;
		case VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_CONFIGURATORE:
			visualizzaArticoliAttiviCategoria();
			break;
		case VISUALIZZA_OFFERTE_IN_SCAMBIO_CHIUSE_CONFIGURATORE:
			offerteCategorie();
			break;
		case ESCI:
			view.stampaArrivederci();
			break;
		default:
			view.stampaVoceMenuNonValida();
		}
	}
	
	/**
	 * metodo che fa visualizzare alla view gli articoli attivi (ricevuti dal model) relativi a una categoria 
	 */
	public void visualizzaArticoliAttiviCategoria() {
		view.stampaArticoliAttiviCategoria(model.getCategorie(), model.getArticoli());
	}
	/**
	 * metodo che fa visualizzare alla view i luoghi relativi a un punto di interesse
	 */
	public void visualizzaLuoghi() {
		view.stampaLuoghi(model.getPunto());
	}
	
	/**
	 * metodo che fa visualizzare alla VIEW l'output della voce di menu selezionata da un FRUITORE 
	 * @param utenteLogin utente loggato
	 * @param risposta voce del menu selezionata dal fruitore nella view  
	 */
	public void menuFruitore(Utente utenteLogin, int risposta) {
		switch (risposta) {
		case VISUALIZZA_LUOGHI_FRUITORE:
			visualizzaLuoghi();
			break;
		case VISUALIZZA_CATEGORIA_FRUITORE:
			visualizzaCategorieRadice();
			break;
		case INSERISCI_ARTICOLO_FRUITORE:
			inserisciArticoloFruitore(utenteLogin);
			break;
		case RITIRA_OFFERTA_FRUITORE:
			ritiraOfferta(utenteLogin);
			break;
		case VISUALIZZA_ARTICOLI_FRUITORE:
			visualizzaArticoliFruitore(utenteLogin);
			break;
		case VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_FRUITORE:
			visualizzaArticoliAttiviCategoria();
			break;
		case EFFETTUA_PROPOSTA_FRUITORE:
			effettuaProposta(utenteLogin);
			break;
		case VISUALIZZA_PROPOSTE_RICEVUTE_FRUITORE:
			visualizzaProposte(utenteLogin);
			break;
		case VISUALIZZA_OFFERTE_FRUITORE:
			visualizzaOfferteFruitore(utenteLogin);
			break;
		case ESCI:
			view.stampaArrivederci();
			break;
		default:
			view.stampaVoceMenuNonValida();
		}
	}
	/**
	 * metodo che fa visualizzare alla view le offerte relative a un fruitore
	 * @param utenteLogin utente loggato
	 */
	public void visualizzaOfferteFruitore(Utente utenteLogin) {
		view.stampaOfferteFruitore(model.getArticoli(), model.getScambi(), utenteLogin);
	}
	
	/**
	 * metodo che esegue le voci del menù d' accesso iniziale richiamando i relativi metodi della view
	 */
	public void accesso() {
		int rispostaLogin;
		do {
			rispostaLogin = view.menuLogin();
			switch (rispostaLogin) {
			case LOGIN:
				try {
					login();
				} catch (PasswordException e) {
					view.stampaErrore(e.getMessage());
				}
				break;
			case REGISTRAZIONE:
				registrazione();
				break;
			case ESCI:
				view.stampaArrivederci();
				break;
			default:
				view.stampaVoceMenuNonValida();
			}
		} while (rispostaLogin != ESCI);
	}
	/**
	 * metodo che esegue la registrazione invocando i rispettivi metodi della view che interagirà con l'utente
	 */
	public void registrazione() {
		String username;
		String nuovaPassword;
		String nuovaPasswordConferma;
		boolean login = false;
		do {
			try {
				username = view.getUsername();
		
				do {
					nuovaPassword = view.getPassword();
					nuovaPasswordConferma = view.getConfermaPassword();
		
					if (!nuovaPassword.equals(nuovaPasswordConferma))
						view.stampaPasswordNonCoincidenti();
				} while (!nuovaPassword.equals(nuovaPasswordConferma));
		
				if(model.registrazione(username, nuovaPassword, nuovaPasswordConferma)) {
					view.stampaRegistrazioneSuccesso();
					login = true;
				}else {
					view.stampaUtenteScelto();
				}
			} catch (UsernameException e) {
				view.stampaErrore(e.getMessage());
			} catch (PasswordException e) {
				view.stampaErrore(e.getMessage());
			}
		}while(!login);
	}
	
	/**
	 * metodo che acquisisce i dati necessari per il menu delle proposte dal model e li passa alla view
	 * @param utenteLogin utente loggato
	 * @param proposta oggetto contenente le informazioni sullo scambio
	 */
	public void menuProposte(Utente utenteLogin, Scambio proposta) {
		if (proposta.getAppuntamento() != null) {
			switch (view.menuProposte()) {
			case CONFERMA_PROPOSTA:
				proposta.risposta(model.getArticoli());
				break;
			case SCEGLI_NUOVO_APPUNTAMENTO:
				model.risposta(proposta, proposta,
						view.getAppuntamento(model.getPunto(), model.getArticoli(), model.getScambi()));
				break;
			case ESCI:
				view.stampaNoRispostaProposta();
				break;
			default:
				view.stampaVoceMenuNonValida();
			}
		} else
			model.risposta(proposta, proposta,
					view.getAppuntamento(model.getPunto(), model.getArticoli(), model.getScambi()));
	}
	
	/**
	 * metodo che passa alla view i dati per visualizzare le proposte
	 * @param utenteLogin oggetto utente loggato
	 */
	public void visualizzaProposte(Utente utenteLogin) {
		Scambio proposta = view.getProposta(model.getArticoli(), model.getScambi(), utenteLogin);
		if(proposta!=null)
			menuProposte(utenteLogin, proposta);
	}
	
	/**
	 * metodo che passa i dati alla view per permettere all'utente di effettuare una proposta
	 * @param utenteLogin oggetto utente loggato
	 */
	public void effettuaProposta(Utente utenteLogin) {
		int idArticoloA = view.getArticoloA(model.getArticoli(), utenteLogin);
		int idArticoloB = view.getArticoloB(model.getArticoli(), model.getScambi(), idArticoloA, utenteLogin);
		if(idArticoloB!=-1)
			model.effettuaProposta(idArticoloA, idArticoloB);
	}
	/**
	 * metodo che passa alla view i dati sugli articoli relativi all'utente loggato
	 * @param utenteLogin oggetto utente loggato
	 */
	public void visualizzaArticoliFruitore(Utente utenteLogin) {
		view.stampaArticoliFruitore(model.getArticoli(), utenteLogin);
	}
	/**
	 * metodo che permette all' utene loggato di ritirare un' offerta
	 * @param utenteLogin oggetto utente loggato
	 */ 
	public void ritiraOfferta(Utente utenteLogin) {
		int id = view.getArticoliAttivi(model.getArticoli(), utenteLogin);
		if(id!=-1)
			model.ritiraOfferta(id);
		else
			view.stampaArticoliAttiviNonPresenti();
	}
	/**
	 * metodo che passa alla VIEW i dati per permettere ad un utente(fruitore) di inserire un nuovo articolo
	 * @param utenteLogin oggetto utente loggato
	 */
	public void inserisciArticoloFruitore(Utente utenteLogin) {
		boolean inserito = model.inserisciArticoloFruitore(view.getArticolo(model.getCategorie(), utenteLogin));
		if (inserito)
			view.stampaArticoloAggiunto();
		else
			view.stampaArticoloNonAggiunto();
	}
	/**
	 * metodo che fa controllare le scadenze al model
	 */
	public void controllaScadenze() {
		model.controllaScadenze();
	}
	/**
	 * metodo che ottiene dal model la lista degli articoli 
	 * @return ritorna una lista di articoli 
	 */
	public ListaArticoli getArticoli() {
		return model.getArticoli();
	}
	/**
	 * metodo che ottiene dal model la lista delle categorie
	 * @return ritorna una lista di categorie
	 */
	public ListaCategorie getCategorie() {
		return model.getCategorie();
	}

}
