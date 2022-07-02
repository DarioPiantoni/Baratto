package it.unibs.elaborato.utente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.unibs.elaborato.exception.PasswordException;
import it.unibs.elaborato.exception.UsernameException;
import it.unibs.elaborato.utils.*;
/**Classe che raggruppa gli utenti presenti iscritti nel sistema in una lista  
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 * @invariant this.utenti != nulling
 */
public class ListaUtenti implements Lista<Utente>,Serializable{
	private static final long serialVersionUID = 1L;
	private static final int NON_TROVATO=-1;
	private List<Utente> utenti;

	/**
	 * Metodo costruttore che istanzia la lista di utenti
	 * @throws UsernameException 
	 * @throws PasswordException 
	 */
	public ListaUtenti() throws PasswordException, UsernameException {
		super();
		this.utenti=new ArrayList<Utente>();
		this.utenti.add(new Configuratore(new Username("admin1")));
		this.utenti.add(new Configuratore(new Username("admin2")));
		
	}
	
	/**
	 * Metodo che permette di aggiungere un utente alla lista
	 * @param nuovoUtente nuovo utente da aggiungere
	 * @return ritorna TRUE se è stato possibile aggiungere l'utente
	 * 
	 * @precondition nuovoUtente!=null
	 * @postcondition utenti.size()<=utenti'.size()
	 */
	public boolean aggiungi(Utente nuovoUtente) {
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
			if(u.getUsername().getUsername().equals(username) && u.getPassword().getPassword().equals(password))
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
			if(this.utenti.get(i).getUsername().getUsername().equals(username))
				return i;
		}
		
		return NON_TROVATO;
	}
	
	
	
	public List<Utente> getUtenti() {
		return utenti;
	}

	/**
	 * Metodo che permette di cambiare la password di un'utente 
	 * @param username username dell' utente a cui si vuole cambiare password
	 * @param nuovaPassword nuova password da inserire 
	 * @return ritorna TRUE se è stato possibile, FALSE altrimenti
	 * @throws PasswordException 
	 * 
	 * @precondition username!=null && nuovaPassword!=null
	 * @postcondition utenti.size()==utenti'.size()
	 */
	public boolean cambioPassword(String username,String nuovaPassword) throws PasswordException {
		int pos=this.cercaUtente(username);
		if(pos!=NON_TROVATO){
			this.utenti.get(pos).setPassword(new Password(nuovaPassword));
			return true;
		}
		return false;
	}
	
	public int size() {
		return this.utenti.size();
	}

	@Override
	public boolean elimina(Utente elemento) {
		return this.utenti.remove(elemento);
	}
}
