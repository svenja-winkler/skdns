package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten, um dem Admin Statistiken darzustellen.
 * 
 */
import java.io.Serializable;

public class Admin implements Serializable {

	private static final long serialVersionUID = -5553597271197251395L;

	private int loginCount;
	private int existingUserCount;

	// Getter-Setter-Methoden
	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount += loginCount;
	}

	public int getExistingUserCount() {
		return existingUserCount;
	}

	public void setExistingUserCount(int existingUserCount) {
		this.existingUserCount = existingUserCount;
	}

}
