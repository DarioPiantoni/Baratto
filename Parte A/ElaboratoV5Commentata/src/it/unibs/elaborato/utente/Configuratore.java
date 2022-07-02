package it.unibs.elaborato.utente;

/**Classe che rappresenta un utente di tipo Configuratore 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class Configuratore extends Utente{
	public static final String PASSWORD_DEFAULT="admin";
	
	public Configuratore(String username) {
		super(username,PASSWORD_DEFAULT);
	}
	

}
