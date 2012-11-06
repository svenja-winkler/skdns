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

	public User(final String pUsername, final String pPassword,
			final String pMail) {
		this.username = pUsername;
		this.setPassword(pPassword);
		this.mail = pMail;
		this.forgottenPassword = false;
	}

	// Getter-Setter-Methoden
	public final String getUsername() {
		return username;
	}

	public final void setUsername(final String pUsername) {
		this.username = pUsername;
	}

	public final String getMail() {
		return mail;
	}

	public final void setMail(final String pMail) {
		this.mail = pMail;
	}

	public final boolean isForgottenPassword() {
		return forgottenPassword;
	}

	public final void setForgottenPassword(final boolean pForgottenPassword) {
		this.forgottenPassword = pForgottenPassword;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(final String pPassword) {
		this.password = pPassword;
	}

	public final String getUserID() {
		return userID;
	}

	public final void setUserID(final String pUserID) {
		this.userID = pUserID;
	}

}
