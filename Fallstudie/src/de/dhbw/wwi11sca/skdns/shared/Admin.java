package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten, 
 * um dem Admin Statistiken darzustellen.
 * 
 */
import java.io.Serializable;

public class Admin implements Serializable {

	private static final long serialVersionUID = -5553597271197251395L;

	private int loginCount;
	private int existingUserCount;
	
	public Admin() { }

	// Getter-Setter-Methoden
	public final int getLoginCount() {
		return loginCount;
	}

	public final void setLoginCount(final int pLoginCount) {
		this.loginCount += pLoginCount;
	}

	public final int getExistingUserCount() {
		return existingUserCount;
	}

	public final void setExistingUserCount(final int pExistingUserCount) {
		this.existingUserCount = pExistingUserCount;
	}

}
