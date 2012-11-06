package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enth�lt alle wichtigen Daten eines Produktes.
 * 
 */
import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID = new String();
	
	private int salesVolume;
	private double price;

	// Konstruktoren
	public Product() {
	}

	public Product(int salesVolume, double price) {
		this.setSalesVolume(salesVolume);
		this.setPrice(price);
	}

	// GEtter-Setter-Methoden
	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
