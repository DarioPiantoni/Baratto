package it.unibs.elaborato.utente;

import java.io.Serializable;
/**Classe che rappresenta il concetto di Utente 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public abstract class Utente implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String username;
	protected String password;
	
	/**
	 * Metodo costruttore 
	 * @param username username del nuovo utente
	 */
	public Utente(String username) {
		super();
		this.username = username;
	}
	
	public Utente(String username,String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	/**
	 * Metodo equals (2 utenti sono uguali se hanno lo stesso username)
	 * @param obj oggetto sul quale effettuare il confronto
	 * @return TRUE se l oggetto passato è un utente e se è lo stesso utente sul quale viene invocato l'equals
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Utente other = (Utente) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
}
