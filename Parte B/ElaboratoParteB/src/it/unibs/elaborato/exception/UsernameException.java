package it.unibs.elaborato.exception;

/**
 * Classe che lancia un eccezione in caso di username errato
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class UsernameException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public UsernameException(String username) {
		super(username+" non valido");
	}
	

}
