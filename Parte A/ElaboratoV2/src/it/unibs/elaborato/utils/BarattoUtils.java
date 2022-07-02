package it.unibs.elaborato.utils;

import it.unibs.elaborato.utente.Configuratore;
import it.unibs.elaborato.utente.Utente;

public class BarattoUtils {
	public static boolean isPrimoAccesso(Utente u) {
		if(u instanceof Configuratore && u.getPassword().equals(Configuratore.PASSWORD_DEFAULT))
			return true;
		return false;
	}
}
