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

	public Machines(int capacity, int serviceLife, int staff,
			double accountingValue) {
		this.setCapacity(capacity);
		this.setServiceLife(serviceLife);
		this.setStaff(staff);
		this.setAccountingValue(accountingValue);
	}

	// Getter-Setter-Methoden
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getServiceLife() {
		return serviceLife;
	}

	public void setServiceLife(int serviceLife) {
		this.serviceLife = serviceLife;
	}

	public double getAccountingValue() {
		return accountingValue;
	}

	public void setAccountingValue(double accountingValue) {
		this.accountingValue = accountingValue;
	}

	public int getStaff() {
		return staff;
	}

	public void setStaff(int staff) {
		this.staff = staff;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
