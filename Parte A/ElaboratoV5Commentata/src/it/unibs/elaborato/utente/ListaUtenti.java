package it.unibs.elaborato.utente;

import java.io.Serializable;
import java.util.ArrayList;
/**Classe che raggruppa gli utenti presenti iscritti nel sistema in una lista  
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 * @invariant this.utenti != nulling
 */
public class ListaUtenti implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final int NON_TROVATO=-1;
	private ArrayList<Utente> utenti;

	/**
	 * Metodo costruttore che istanzia la lista di utenti
	 */
	public ListaUtenti() {
		super();
		this.utenti=new ArrayList<Utente>();
		this.utenti.add(new Configuratore("admin1"));
		this.utenti.add(new Configuratore("admin2"));
	}
	
	/**
	 * Metodo che permette di aggiungere un utente alla lista
	 * @param nuovoUtente nuovo utente da aggiungere
	 * @return ritorna TRUE se è stato possibile aggiungere l'utente
	 * 
	 * @precondition nuovoUtente!=null
	 * @postcondition utenti.size()<=utenti'.size()
	 */
	public boolean aggiungiUtente(Utente nuovoUtente) {
		if(!this.utenti.contains(nuovoUtente)){
			this.utenti.add(nuovoUtente);
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo che seleziona un Utente dalla lista 
	 * @param username username dell'utente da selezionare
	 * @param password password 
	 * @return ritorna un oggetto Utente se è stato trovato, NULL altrimenti
	 * 
	 * @precondition username!=null && password!=null
	 * @postcondition 
	 */
	public Utente loginUtente(String username,String password) {
		for(Utente u:utenti) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password))
				return u;
		}
		return null;
			
	}
	/**
	 * metodo che ricerca un' utente nella lista tramite l' username
	 * @param username username dell'utente da trovare
	 * @return ritorna la posizione dell'utente nell'array se trovato, -1 altrimenti
	 * 
	 * @precondition username!=null
	 * @postcondition 0<=i<=utenti.size() || i==-1
	 */
	public int cercaUtente(String username) {
		for(int i=0;i<this.utenti.size();i++) {
			if(this.utenti.get(i).getUsername().equals(username))
				return i;
		}
		
		return NON_TROVATO;
	}
	
	/**
	 * Metodo che permette di cambiare la password di un'utente 
	 * @param username username dell' utente a cui si vuole cambiare password
	 * @param nuovaPassword nuova password da inserire 
	 * @return ritorna TRUE se è stato possibile, FALSE altrimenti
	 * 
	 * @precondition username!=null && nuovaPassword!=null
	 * @postcondition utenti.size()==utenti'.size()
	 */
	public boolean cambioPassword(String username,String nuovaPassword) {
		int pos=this.cercaUtente(username);
		if(pos!=NON_TROVATO){
			this.utenti.get(pos).setPassword(nuovaPassword);
			return true;
		}
		return false;
	}
	
	public int size() {
		return this.utenti.size();
	}
	


	@Override
	public String toString() {
		if(!utenti.isEmpty()) {
			String testo="Lista utenti\n";
			for(Utente u:utenti)
				testo+=u.getUsername()+"\n";
			return testo;
		}else
			return "Non sono presenti utenti";
	}
}
