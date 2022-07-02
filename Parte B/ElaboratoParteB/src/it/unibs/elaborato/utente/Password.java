package it.unibs.elaborato.utente;

import java.io.Serializable;

import it.unibs.elaborato.exception.PasswordException;

/**Classe che rappresenta una password per un account utente 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class Password implements Serializable{
	private String password;
	private static final int MIN_LUNGHEZZA=5;

	public Password(String password) throws PasswordException {
		super();
		setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws PasswordException{
		if(password.length()<MIN_LUNGHEZZA)
			throw new PasswordException();
		else
			this.password=password;
	}
}
