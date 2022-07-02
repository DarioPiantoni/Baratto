package it.unibs.elaborato.utente;


public class Configuratore extends Utente{
	public static final String PASSWORD_DEFAULT="admin";
	
	public Configuratore(String username) {
		super(username,PASSWORD_DEFAULT);
	}
	

}
