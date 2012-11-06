package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten einer Maschine.
 * 
 */
import java.io.Serializable;

public class Machines implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID = new String();

	private int capacity;
	private int serviceLife;
	private int staff;
	private double accountingValue;

	// Konstruktoren
	public Machines() {
	}

	public Machines(final int pCapacity, final int pServiceLife, 
			final int pStaff, final double pAccountingValue) {
		this.setCapacity(pCapacity);
		this.setServiceLife(pServiceLife);
		this.setStaff(pStaff);
		this.setAccountingValue(pAccountingValue);
	}

	// Getter-Setter-Methoden
	public final int getCapacity() {
		return capacity;
	}

	public final void setCapacity(final int pCapacity) {
		this.capacity = pCapacity;
	}

	public final int getServiceLife() {
		return serviceLife;
	}

	public final void setServiceLife(final int pServiceLife) {
		this.serviceLife = pServiceLife;
	}

	public final double getAccountingValue() {
		return accountingValue;
	}

	public final void setAccountingValue(final double pAccountingValue) {
		this.accountingValue = pAccountingValue;
	}

	public final int getStaff() {
		return staff;
	}

	public final void setStaff(final int pStaff) {
		this.staff = pStaff;
	}

	public final String getUserID() {
		return userID;
	}

	public final void setUserID(final String pUserID) {
		this.userID = pUserID;
	}

}
