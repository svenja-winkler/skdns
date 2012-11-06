package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten eines Unternehmens.
 * 
 */
import java.io.Serializable;

public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID = new String();
	private String companyID = new String();
	private int topLine;
	private int amount;
	private double marketShare;
	private Product product;
	private String tradeName;

	// Konstruktoren
	public Company() {
	}

	public Company(final int pTopLine, final int pAmount,
			final double pMarketShare) {
		this.setTopLine(pTopLine);
		this.setAmount(pAmount);
		this.setMarketShare(pMarketShare);

	}

	public Company(final int pTopLine, final int pAmount,
			final double pMarketShare, final Product pProduct) {
		this.setTopLine(pTopLine);
		this.setAmount(pAmount);
		this.setMarketShare(pMarketShare);
		this.setProduct(pProduct);
	}

	public Company(final String pUserID, final String pCompanyID, 
			final String pTradeName, final Product pProduct) {
		this.userID = pUserID;
		this.companyID = pCompanyID;
		this.tradeName = pTradeName;
		this.product = pProduct;
	}

	public Company(final String pUserID, final String pCompanyID, 
			final String pTradeName, final int pTopLine, 
			final double pMarketShare, final Product pProduct) {
		this.userID = pUserID;
		this.companyID = pCompanyID;
		this.tradeName = pTradeName;
		this.topLine = pTopLine;
		this.marketShare = pMarketShare;
		this.product = pProduct;
	}

	// Getter-Setter-Methoden
	public final String getCompanyID() {
		return companyID;
	}

	public final void setCompanyID(final String pCompanyID) {
		this.companyID = pCompanyID;
	}

	public final int getTopLine() {
		return topLine;
	}

	public final void setTopLine(final int pTopLine) {
		this.topLine = pTopLine;
	}

	public final int getAmount() {
		return amount;
	}

	public final void setAmount(final int pAmount) {
		this.amount = pAmount;
	}

	public final double getMarketShare() {
		return marketShare;
	}

	public final void setMarketShare(final double pMarketShare) {
		this.marketShare = pMarketShare;
	}

	public final Product getProduct() {
		return product;
	}

	public final void setProduct(final Product pProduct) {
		this.product = pProduct;
	}

	public final String getUserID() {
		return userID;
	}

	public final void setUserID(final String pUserID) {
		this.userID = pUserID;
	}

	public final String getTradeName() {
		return tradeName;
	}

	public final void setTradeName(final String pTradeName) {
		this.tradeName = pTradeName;
	}

}
