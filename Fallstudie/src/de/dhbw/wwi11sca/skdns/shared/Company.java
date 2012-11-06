package de.dhbw.wwi11sca.skdns.shared;

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

	public Company() {
	}

	public Company(String userID, String companyID, String tradeName,
			Product product) {
		this.userID = userID;
		this.companyID = companyID;
		this.tradeName = tradeName;
		this.product = product;
	}

	public Company(String userID, String companyID, String tradeName,
			int topLine, double marketShare, Product product) {
		this.userID = userID;
		this.companyID = companyID;
		this.tradeName = tradeName;
		this.topLine = topLine;
		this.marketShare = marketShare;
		this.product = product;
	}

	public Company(int topLine, int amount, double marketShare, Product product) {
		this.setTopLine(topLine);
		this.setAmount(amount);
		this.setMarketShare(marketShare);
		this.setProduct(product);
	}

	public Company(int topLine, int amount, double marketShare) {
		this.setTopLine(topLine);
		this.setAmount(amount);
		this.setMarketShare(marketShare);

	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public int getTopLine() {
		return topLine;
	}

	public void setTopLine(int topLine) {
		this.topLine = topLine;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getMarketShare() {
		return marketShare;
	}

	public void setMarketShare(double marketShare) {
		this.marketShare = marketShare;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

}
