package it.unibs.elaborato.utente;

import java.io.Serializable;

public abstract class Utente implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String username;
	protected String password;
	
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
