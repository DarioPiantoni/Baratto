package it.unibs.elaborato.utente;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaUtenti implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final int NON_TROVATO=-1;
	private ArrayList<Utente> utenti;

	public ListaUtenti() {
		super();
		this.utenti=new ArrayList<Utente>();
		this.utenti.add(new Configuratore("admin1"));
		this.utenti.add(new Configuratore("admin2"));
	}
	
	public boolean aggiungiUtente(Utente nuovoUtente) {
		if(!this.utenti.contains(nuovoUtente)){
			this.utenti.add(nuovoUtente);
			return true;
		}
		return false;
	}
	
	public Utente loginUtente(String username,String password) {
		for(Utente u:utenti) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password))
				return u;
		}
		return null;
			
	}
	
	public int cercaUtente(String username) {
		for(int i=0;i<this.utenti.size();i++) {
			if(this.utenti.get(i).getUsername().equals(username))
				return i;
		}
		
		return NON_TROVATO;
	}
	
	public boolean cambioPassword(String username,String nuovaPassword) {
		int pos=this.cercaUtente(username);
		if(pos!=NON_TROVATO){
			this.utenti.get(pos).setPassword(nuovaPassword);
			return true;
		}
		return false;
	}
	


	@Override
	public String toString() {
		String testo="Lista utenti\n";
		for(Utente u:utenti)
			testo+=u.getUsername()+" "+u.getPassword()+"\n";
		return testo;
	}
}
