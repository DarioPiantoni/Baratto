package it.unibs.elaborato.exception;

/**
 * Classe che gestisce le eccezioni dovute all'inserimento di una password errata 
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class PasswordException extends Exception{
	private static final long serialVersionUID = 1L;
	
	
	public PasswordException() {
		super("Password non valida");
	}
	

}
