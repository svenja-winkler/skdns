package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten eines Produktes.
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

	public Product(final int pSalesVolume, final double pPrice) {
		this.setSalesVolume(pSalesVolume);
		this.setPrice(pPrice);
	}

	// GEtter-Setter-Methoden
	public final int getSalesVolume() {
		return salesVolume;
	}

	public final void setSalesVolume(final int pSalesVolume) {
		this.salesVolume = pSalesVolume;
	}

	public final double getPrice() {
		return price;
	}

	public final void setPrice(final double pPrice) {
		this.price = pPrice;
	}

	public final String getUserID() {
		return userID;
	}

	public final void setUserID(final String pUserID) {
		this.userID = pUserID;
	}

}
