package it.unibs.elaborato.utils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.mercato.Appuntamento;
import it.unibs.elaborato.mercato.Giorno;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.Scambio;
import it.unibs.elaborato.mercato.Token;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.CampoCompilabile;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.Offerta;
import it.unibs.elaborato.utente.Fruitore;
import it.unibs.elaborato.utente.Utente;
/**Classe di utilità che permette ad un Fruitore di effettuare operazioni sul sistema 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class FruitoreUtils {
	private static final String TITOLO_MENU = "Baratto";
	private static final int ESCI = 0;

	// costanti menù fruitore
	private static final String[] VOCI_MENU_FRUITORE = { "Visualizza luoghi", "Visualizza categorie",
			"Inserisci un nuovo articolo", "Ritira offerta", "Visualizza i miei articoli",
			"Visualizza articoli attivi di una categoria", "Effettua una proposta", "Apri casella proposte ricevute",
			"Visualizza le mie offerte" };
	private static final int VISUALIZZA_LUOGHI_FRUITORE = 1;
	private static final int VISUALIZZA_CATEGORIA_FRUITORE = 2;
	private static final int INSERISCI_ARTICOLO_FRUITORE = 3;
	private static final int RITIRA_OFFERTA_FRUITORE = 4;
	private static final int VISUALIZZA_ARTICOLI_FRUITORE = 5;
	private static final int VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_FRUITORE = 6;
	private static final int EFFETTUA_PROPOSTA_FRUITORE = 7;
	private static final int VISUALIZZA_PROPOSTE_RICEVUTE_FRUITORE = 8;
	private static final int VISUALIZZA_OFFERTE_FRUITORE = 9;

	// costanti menù proposte
	private static final String TITOLO_MENU_PROPOSTA = "Scegli";
	private static final String[] VOCI_MENU_PROPOSTA = { "Conferma la proposta", "Scegli un nuovo appuntamento" };
	private static final int CONFERMA_PROPOSTA = 1;
	private static final int SCEGLI_NUOVO_APPUNTAMENTO = 2;
	
	/**
	 * Metodo che visualizza a video il menu di interazione per un fruitore
	 * @param categorie lista delle categorie
	 * @param punto lista dei punti di interesse
	 * @param articoli lista degli articoli
	 * @param scambi lista degli scambi
	 * @param utenteLogin utente
	 */
	public static void menuFruitore(ListaCategorie categorie, PuntoInteresse punto,
			ListaArticoli articoli, ListaScambi scambi, Utente utenteLogin) {
		MyMenu menu = new MyMenu(TITOLO_MENU, VOCI_MENU_FRUITORE);
		int risposta;
		do {
			risposta = menu.scegli();

			switch (risposta) {
			case VISUALIZZA_LUOGHI_FRUITORE:
				BarattoUtils.visualizzaLuoghi(punto);
				break;
			case VISUALIZZA_CATEGORIA_FRUITORE:
				visualizzaCategorieRadice(categorie);
				break;
			case INSERISCI_ARTICOLO_FRUITORE:
				inserisciArticoloFruitore(categorie, articoli, utenteLogin);
				break;
			case RITIRA_OFFERTA_FRUITORE:
				ritiraOfferta(articoli, utenteLogin);
				break;
			case VISUALIZZA_ARTICOLI_FRUITORE:
				visualizzaArticoliFruitore(articoli, utenteLogin);
				break;
			case VISUALIZZA_ARTICOLI_ATTIVI_CATEGORIA_FRUITORE:
				BarattoUtils.visualizzaArticoliAttiviCategoria(categorie, articoli);
				break;
			case EFFETTUA_PROPOSTA_FRUITORE:
				effettuaProposta(articoli, scambi, utenteLogin);
				break;
			case VISUALIZZA_PROPOSTE_RICEVUTE_FRUITORE:
				visualizzaProposte(punto, articoli, scambi, utenteLogin);
				break;
			case VISUALIZZA_OFFERTE_FRUITORE:
				visualizzaOfferteFruitore(articoli, scambi, utenteLogin);
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
	 * Metodo che permette ad un fruitore di aggiugnere un articolo al sistema 
	 * @param categorie lista delle categorie 
	 * @param articoli lista degli articoli
	 * @param utenteLogin utente che vuole aggiungere l' articolo
	 */
	private static void inserisciArticoloFruitore(ListaCategorie categorie, ListaArticoli articoli, Utente utenteLogin) {
		Articolo articoloInserito=inserisciArticolo(categorie, utenteLogin);
		if(articoloInserito!=null) {
			articoli.aggiungiArticolo(articoloInserito);
			System.out.println("Articolo aggiunto correttamente");
		}
		else
			System.out.println("Articolo non aggiunto");
	}
	
	/**
	 * Metodo che visualizza a video tutte le categorie radice (categorie senza una categoria padre) 
	 * @param categorie lista delle categorie
	 */
	private static void visualizzaCategorieRadice(ListaCategorie categorie) {
		System.out.println(categorie.visualizzaCategorieRadice());
	}

	/**
	 * Metodo che chiede all'utente di inserire i dati relativi all' articolo da aggiugnere
	 * @param categorie lista delle categorie 
	 * @param utenteLogin utente prorpietario dell' articolo da aggiungere
	 * @return ritorna un oggetto articolo
	 */
	private static Articolo inserisciArticolo(ListaCategorie categorie, Utente utenteLogin) {
		String nome = InputDati.leggiStringaNonVuota("Inserisci il nome\n");
		Categoria categoria = BarattoUtils.scegliCategoria(categorie);
		
		Articolo articolo=null;
		if(categoria!=null) {
			ArrayList<CampoCompilabile> campiCompilabili = inserisciCampiArticolo(categorie, categoria);
	
			articolo=new Articolo((Fruitore) utenteLogin, nome, categoria, campiCompilabili);
			articolo.cambioStatoOfferta(Offerta.APERTA);
		}
		return articolo;
	}
	
	/**
	 * Metodo che chiede all'utente di inserire i campi relativi a un'articolo
	 * @param categorie lista delle categorie
	 * @param categoria categoria relativo all'articolo in creazione
	 * @return ritorna una lista di CampiCompilabili 
	 */
	private static ArrayList<CampoCompilabile> inserisciCampiArticolo(ListaCategorie categorie, Categoria categoria) {
		ArrayList<Campo> campi = categorie.getCampiCategoria(categoria);
		ArrayList<CampoCompilabile> campiCompilabili = new ArrayList<CampoCompilabile>();
		boolean erroreTipo;
		
		if(!campi.isEmpty()) {
			System.out.println("Inserisci i campi");
			for (Campo c : campi) {
				do {
					erroreTipo=false;
					try {
						String dato;
						if (c.isObbligatorio())
							dato = InputDati.leggiStringaNonVuota(c.getNome() + "\n");
						else
							dato = InputDati.leggiStringa(c.getNome() + " (FACOLTATIVO)\n");
						campiCompilabili.add(new CampoCompilabile(c, dato));
					}catch(NumberFormatException e) {
						System.out.println("Attenzione questo campo è di tipo "+c.getTipo().toString());
						erroreTipo=true;
					}catch(IllegalArgumentException e) {
						System.out.println(e.getMessage());
						erroreTipo=true;
					}
				}while(erroreTipo);
			}
		}else
			System.out.println("Non ci sono campi compilabili per questa categoria");
		
		return campiCompilabili;
	}
	/**
	 * Metodo che permette ad un Fruitore di ritirare un'offerta tra quelle in corso
	 * @param articoli lista degli articoli
	 * @param utenteLogin utente che vuole ritirare un'offerta
	 */
	private static void ritiraOfferta(ListaArticoli articoli, Utente utenteLogin) {
		System.out.println("Scegli un articolo da ritirare");
		int ris=cambiaStatoOfferta(articoli, utenteLogin, Offerta.RITIRATA);
		if(ris==-1)
			System.out.println("Non ci sono articoli da ritirare");
		else 
			System.out.println("Articolo ritirato correttamente");
			
	}
	
	/**
	 * Metodo che permette ad un fruitore cambiare un'offerta 
	 * @param articoli lista degli articoli
	 * @param utenteLogin utente che vuole modificare un'offerta
	 * @param offerta offerta sulla quale effetuare la modifica 
	 * @return ritorna l' indice dell'articolo sul quale si vuole modificare l' offerta , -1 se non trovato
	 */
	private static int cambiaStatoOfferta(ListaArticoli articoli, Utente utenteLogin, Offerta offerta) {
		int id=selezionaArticolo(articoli, utenteLogin);
		if(id!=-1)
			articoli.getArticolo(id).cambioStatoOfferta(offerta);
		return id;

	}

	/**Metodo per selezionare un articolo dalla lista degli articoli attivi di un fruitore
	 * @param articoli lista degli articoli
	 * @param utenteLogin fruitore
	 * @return ritorna l' indice dell'articolo selezionato , -1 se non trovato
	 */
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
	
	/**
	 * Metodo che visualizza a video gli articoli relativi ad un fruitore
	 * @param articoli lista degli articoli 
	 * @param utenteLogin utente fruitore 
	 */
	private static void visualizzaArticoliFruitore(ListaArticoli articoli, Utente utenteLogin) {
		HashMap<Integer, Articolo> articoliFruitore = articoli.getArticoliFruitore((Fruitore) utenteLogin);
		if(!articoliFruitore.isEmpty()) {
			System.out.println("Articoli di " + utenteLogin.getUsername());
			
			for (Articolo a : articoliFruitore.values())
				System.out.println(a.toString());
		}else
			System.out.println("Non ci sono articoli");
	}
	/**
	 * Metodo che permette ad un fruitore di effettuare una proposta 
	 * @param articoli lista articoli
	 * @param scambi lista scami  
	 * @param utenteLogin utente fruitore che vuole effettuare l' operazione
	 */
	private static void effettuaProposta(ListaArticoli articoli, ListaScambi scambi, Utente utenteLogin) {
		System.out.println("Scegli un tuo articolo da scambiare");
		int idArticoloA = selezionaArticolo(articoli, utenteLogin);

		if (idArticoloA != -1) {
			Articolo articoloSelezionato = articoli.getArticolo(idArticoloA);

			StringBuffer testo = new StringBuffer("Scegli un articolo con cui fare scambio\n");
			HashMap<Integer, Articolo> articoliCategoria = articoli.getArticoliAttiviCategoriaNoFruitore(articoloSelezionato.getCategoria(), (Fruitore) utenteLogin);
			if (!articoliCategoria.isEmpty()) {
				ArrayList<Integer> chiavi = new ArrayList<Integer>();

				int i = 0;
				for (int chiave : articoliCategoria.keySet()) {
					testo.append((i + 1) + "->" + articoliCategoria.get(chiave).toString() + "\n");
					chiavi.add(chiave);
					i++;
				}
				
				testo.append("->");

				int indice = InputDati.leggiIntero(testo.toString(), 1, articoliCategoria.size());

				int idArticoloB = chiavi.get(indice - 1);
				
				articoli.getArticolo(idArticoloA).cambioStatoOfferta(Offerta.ACCOPPIATA);
				articoli.getArticolo(idArticoloB).cambioStatoOfferta(Offerta.SELEZIONATA);
				scambi.aggiungiScambio(new Scambio(idArticoloA, idArticoloB));
			} else
				System.out.println("Non ci sono articoli con cui fare scambio");
		} else
			System.out.println("Non ci sono articoli da scambiare");
	}
	/**
	 * Metodo che visualizza a video le proposte a cui un fruitore puo rispondere
	 * @param puntiInteresse lista dei punti di interesse
	 * @param articoli lista degli articoli 
	 * @param scambi lista degli scambi 
	 * @param utenteLogin utente fruitore che vuole visualizzare le proposte
	 */
	private static void visualizzaProposte(PuntoInteresse punto, ListaArticoli articoli,ListaScambi scambi, Utente utenteLogin) {
		ArrayList<Scambio> proposteRicevute = scambi.getProposte((Fruitore) utenteLogin, articoli);

		if (!proposteRicevute.isEmpty()) {
			StringBuffer testo = new StringBuffer("Scegli una delle proposte a cui rispondere (0 per non rispondere a nessuno)\n");
			for (int i = 0; i < proposteRicevute.size(); i++)
				testo.append((i + 1) + "->" + proposteRicevute.get(i).visualizza(articoli) + "\n");
			
			testo.append("->");

			int indice = InputDati.leggiIntero(testo.toString(), 0, proposteRicevute.size());
			Scambio proposta;
			if (indice != 0) {
				proposta = proposteRicevute.get(indice - 1);
				Scambio propostaCopia = proposta;
				if (proposta.getAppuntamento() != null) {
					MyMenu menuProposta = new MyMenu(TITOLO_MENU_PROPOSTA, VOCI_MENU_PROPOSTA);
					int risposta = menuProposta.scegli();

					switch (risposta) {
					case CONFERMA_PROPOSTA:
						proposta.risposta(articoli);
						break;
					case SCEGLI_NUOVO_APPUNTAMENTO:
						scegliAppuntamento(punto, articoli, scambi, proposta, propostaCopia);
						break;
					case ESCI:
						System.out.println("Non hai risposto a questa proposta");
						break;
					default:
						System.out.println("Voce di menù non valida");
					}
				} else
					scegliAppuntamento(punto, articoli, scambi, proposta, propostaCopia);

			}
		} else
			System.out.println("Non ci sono proposte in arrivo");
	}
	
	/**
	 * Metodo che visualizza a video le offerte avanzate da un fruitore
	 * @param articoli lista degli articoli
	 * @param scambi lista degli scambi
	 * @param utenteLogin utente
	 */
	private static void visualizzaOfferteFruitore(ListaArticoli articoli, ListaScambi scambi, Utente utenteLogin) {
		ArrayList<Scambio> offerteFruitore = scambi.getOfferteFruitore((Fruitore) utenteLogin, articoli);
		
		if(!offerteFruitore.isEmpty()) {
			for (Scambio s : offerteFruitore) {
				System.out.println(s.visualizza(articoli));
				if (articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.CHIUSA))
					System.out.println("Stato: Offerta chiusa");
				else {
					if (s.getToken().equals(Token.A))
						System.out.println("Stato: Ora tocca a te rispondere");
					else
						System.out.println("Stato: In attesa di una risposta dell'altro fruitore");
				}
				System.out.println("");
			}
		}else
			System.out.println("Non ci sono offerte");
	}
		/**
		 * Metodo che permette ad un fruitore di scegliere un appuntamento
		 * @param puntiInteresse lista dei punti di interesse
		 * @param articoli lista articoli  
		 * @param scambi lista scambi
		 * @param proposta lista delle proposte
		 * @param propostaCopia vecchia proposta che dovrà essere eliminata dalla lista
		 */
	private static void scegliAppuntamento(PuntoInteresse punto, ListaArticoli articoli,ListaScambi scambi, Scambio proposta, Scambio propostaCopia) {
		StringBuffer testo;
		int indice;
		
		if(punto != null)
		{
			System.out.println("Stai operando sulla piazza " + punto.getPiazza());
	
			testo = new StringBuffer("Scegli il luogo: \n");
			for (int i = 0; i < punto.getLuoghi().size(); i++)
				testo.append((i + 1) + "->" + punto.getLuoghi().get(i) + "\n");
	
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
				} while (ora.getHour() < oraFine.getHour() || ora.getHour() == oraFine.getHour() && ora.getMinute() < oraFine.getMinute() || ora.getHour() == oraFine.getHour() && ora.getMinute() == oraFine.getMinute());
			}
			indice = InputDati.leggiIntero(testo.toString(), 1, orariInserire.size());
			ora = orariInserire.get(indice-1);
			
			Appuntamento appuntamento = new Appuntamento(punto.getPiazza(), luogo, giorno, ora);
			Token token;
			if (proposta.getToken().equals(Token.A))
				token = Token.B;
			else
				token = Token.A;
			proposta.risposta(articoli, appuntamento, token);
			scambi.eliminaScambio(propostaCopia);
			scambi.aggiungiScambio(proposta);
		}else
			System.out.println("Non ci sono punti di interesse");
	}
}
