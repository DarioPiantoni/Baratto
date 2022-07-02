package it.unibs.elaborato.utente;

import java.io.Serializable;

import it.unibs.elaborato.exception.UsernameException;

/**Classe che rappresenta l' username valido per un utente 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class Username implements Serializable{
	private String username;

	public Username(String username) throws UsernameException{
		super();
		setUsername(username);
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) throws UsernameException{
		if(!Character.isLetter(username.charAt(0)))
			throw new UsernameException(username);
		else
			this.username=username;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Username other = (Username) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
