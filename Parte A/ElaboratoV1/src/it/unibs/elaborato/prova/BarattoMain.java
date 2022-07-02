package it.unibs.elaborato.prova;

import java.util.ArrayList;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.categoria.TipoDato;
import it.unibs.elaborato.utente.*;
import it.unibs.elaborato.utils.*;

public class BarattoMain {
	private static final String MESSAGGIO_BENVENUTO="Buongiorno, %s\n";
	private static final String TITOLO_MENU="Baratto";
	private static final String[] VOCI_MENU= {"Inserisci una nuova categoria",
											   "Visualizza le categorie"};
	private static final int INSERISCI_CATEGORIA=1;
	private static final int VISUALIZZA_CATEGORIA=2;
	private static final int ESCI=0;
	
	//costanti per i file
	private static final String PERCORSO_DATI="src\\it\\unibs\\elaborato\\dati";
	private static final String NOME_FILE_UTENTI="Utenti.dat";
	private static final String NOME_FILE_CATEGORIE="Categorie.dat";
	public static void main(String[] args) {
		String username,password;
		String nuovaPassword,nuovaPasswordConferma;
		Utente utenteLogin;
		
		MyMenu menu=new MyMenu(TITOLO_MENU,VOCI_MENU);
		int risposta;
		
		ListaUtenti utenti;
		ListaCategorie categorie;
		
		boolean login=false;
		
		//carico dai file
		utenti=FileUtils.caricaFile(PERCORSO_DATI,NOME_FILE_UTENTI);
		if(utenti==null)
			utenti=new ListaUtenti();
		else
			System.out.println(NOME_FILE_UTENTI+" è stato caricato correttamente");
			
		
		categorie=FileUtils.caricaFile(PERCORSO_DATI,NOME_FILE_CATEGORIE);
		if(categorie==null)
			categorie=new ListaCategorie();
		else
			System.out.println(NOME_FILE_CATEGORIE+" è stato caricato correttamente");
		
		do {
			username=InputDati.leggiStringaNonVuota("Inserisci il tuo username\n");
			password=InputDati.leggiStringaNonVuota("Inserisci la password\n");
			
			utenteLogin=utenti.loginUtente(username,password);
			
			if(utenteLogin!=null) {
				if(BarattoUtils.isPrimoAccesso(utenteLogin)){
					do {
						nuovaPassword=InputDati.leggiStringaNonVuota("Inserisci una nuova password\n");
						nuovaPasswordConferma=InputDati.leggiStringaNonVuota("Conferma la nuova password\n");
						
						if(nuovaPassword.equals(nuovaPasswordConferma))
							utenti.cambioPassword(username, nuovaPassword);
						else
							System.out.println("Non coincidono le password da te inserite");
					
					}while(!nuovaPassword.equals(nuovaPasswordConferma));
				}
				System.out.printf(MESSAGGIO_BENVENUTO,username);
				login=true;
			}
			else
				System.out.println("Username o password errata");
		
		}while(!login);
		
		do {
			risposta=menu.scegli();
			
			switch(risposta) {
				case INSERISCI_CATEGORIA:
		          inserisciCategoria(categorie);
		        break;
				case VISUALIZZA_CATEGORIA:
					visualizzaCategorie(categorie);
				break;
				case ESCI:
					System.out.println("Arrivederci");
				break;
				default:
					System.out.println("Voce di menù non valida");
			}
		}while(risposta!=ESCI);

		//salvo i dati su file
		boolean salvatoUtenti=FileUtils.salvaFile(utenti,PERCORSO_DATI, NOME_FILE_UTENTI);
		boolean salvatoCategorie=FileUtils.salvaFile(categorie,PERCORSO_DATI, NOME_FILE_CATEGORIE);
		
		if(salvatoUtenti && salvatoCategorie)
			System.out.println("Salvataggio riuscito");
		else
			System.out.println("Salvataggio non riuscito");
		
	}


	private static void inserisciCategoria(ListaCategorie categorie) {
	    boolean ok = false;
	    System.out.println("Inserisci una categoria");
	    
	    do {
	      
	      String nomeCategoria = InputDati.leggiStringaNonVuota("Inserisci il nome\n");
	      if(controlloRadici(categorie, nomeCategoria))
	        ok = false;
	      else
	        ok = true;
	      
	      if(ok) {
	        String descrizioneCategoria = InputDati.leggiStringaNonVuota("Inserisci la descrizione\n");
	        ArrayList<Campo> campi = inserisciCampi();
	        Categoria c = new Categoria(nomeCategoria, descrizioneCategoria, campi,null);
	        categorie.aggiungiCategoria(c);
	        sottoCategoria(categorie, c, c);
	      }
	      
	    }while(!ok);
	  }
	  
	  private static boolean controlloRadici(ListaCategorie categorie, String radice) {
	    ArrayList<Categoria> cLista = categorie.getRadici();
	    
	    for(Categoria c: cLista) {
	      if(c.getNome().equalsIgnoreCase(radice)) {
	        System.out.println("Radice già presente");
	        return true;
	      }
	    }
	    
	    return false;
	  }

	private static ArrayList<Campo> inserisciCampi() {
		String scelta;
		ArrayList<Campo> campi=new ArrayList<Campo>();
		
		System.out.println("Inserisci i campi compilabili della categoria");
		do {
			scelta=InputDati.leggiStringaNonVuota("Vuoi inserire campi compilabili?(Sì/No)\n");
			if(scelta.equalsIgnoreCase("Sì")) {
				Campo nuovoCampo=insersciCampo();
				campi.add(nuovoCampo);
			}
			
		}while(scelta.equalsIgnoreCase("Sì"));
		return campi;
	}

	private static Campo insersciCampo() {
		String nomeCampo=InputDati.leggiStringaNonVuota("Inserisci il nome campo\n");
		int tipoCampo=InputDati.leggiIntero("Inserisci il tipo del campo (1->Parola, 2->Intero, 3->Decimale, 4->Vero/Falso\n",1,4);
		TipoDato tipoDato=null;
		switch(tipoCampo) {
			case 1:
				tipoDato=TipoDato.PAROLA;
				break;
			case 2:
				tipoDato=TipoDato.INTERO;
				break;
			case 3:
				tipoDato=TipoDato.DECIMALE;
				break;
			case 4:
				tipoDato=TipoDato.VEROFALSO;
				break;
		}
		String campoOpzionale=InputDati.leggiStringaNonVuota("Il campo è opzionale (Sì/No)\n");
		boolean isOpzionale;
		if(campoOpzionale.equalsIgnoreCase("Sì"))
			isOpzionale=true;
		else
			isOpzionale=false;
		Campo nuovoCampo=new Campo(nomeCampo,tipoDato,isOpzionale);
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
	      if(controlloSottoCategoria(categorie, radice, nomeCategoria))
	        ok = false;
	      else
	        ok = true;
	      
	      if(ok) {
	        String descrizioneCategoria = InputDati.leggiStringaNonVuota("Inserisci la descrizione\n");
	        ArrayList<Campo> campi = inserisciCampi();
	        Categoria c = new Categoria(nomeCategoria, descrizioneCategoria, campi, categoriaPadre);
	        categorie.aggiungiCategoria(c);
	        sottoCategoria(categorie, c, radice);
	      }
	      
	    }while(!ok);
	  }
	   
	  private static boolean controlloSottoCategoria(ListaCategorie categorie, Categoria radice, String sottoCategoria) {
	    ArrayList<Categoria> cLista = categorie.getAlbero(radice);
	    
	    for(Categoria c: cLista) {  
	      if(c.getNome().equalsIgnoreCase(sottoCategoria)) {
	        System.out.println("Categoria già presente");
	        return true;
	      }
	    }
	    
	    return false;
	  }
	
	private static void visualizzaCategorie(ListaCategorie categorie) {
		System.out.println(categorie.toString());
	}

}
