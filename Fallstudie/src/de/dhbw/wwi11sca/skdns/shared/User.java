package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten eines Users.
 * 
 */
import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID = new String();
	
	private String username = new String();
	private String password = new String();
	private String mail = new String();
	
	private boolean forgottenPassword = false;

	// Konstruktoren
	public User() {
	}

	public User(String username, String password, String mail) {
		this.username = username;
		this.setPassword(password);
		this.mail = mail;
		this.forgottenPassword = false;
	}

	// Getter-Setter-Methoden
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isForgottenPassword() {
		return forgottenPassword;
	}

	public void setForgottenPassword(boolean forgottenPassword) {
		this.forgottenPassword = forgottenPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
