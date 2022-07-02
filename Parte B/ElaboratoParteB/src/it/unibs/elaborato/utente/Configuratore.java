package it.unibs.elaborato.utente;

import it.unibs.elaborato.exception.PasswordException;

/**Classe che rappresenta un utente di tipo Configuratore 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class Configuratore extends Utente{
	public static final String PASSWORD_DEFAULT="admin";
	
	public Configuratore(Username username) throws PasswordException{
		super(username,new Password(PASSWORD_DEFAULT));
	}
	

}
