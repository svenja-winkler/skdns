package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID = new String();
	private String companyID = new String();
	private int topLine;
	private int amount;
	private double marketShare;
	private String trendOfRequest;
	private Product product;
	private String tradeName;

	public Company() {
	}

	public Company(int topLine, int amount, double marketShare,
			String trendOfRequest, Product product) {
		this.setTopLine(topLine);
		this.setAmount(amount);
		this.setMarketShare(marketShare);
		this.setTrendOfRequest(trendOfRequest);
		this.setProduct(product);
	}

	public Company(int topLine, int amount, double marketShare,
			String trendOfRequest) {
		this.setTopLine(topLine);
		this.setAmount(amount);
		this.setMarketShare(marketShare);
		this.setTrendOfRequest(trendOfRequest);

	}

	public String getCompanyID(){
		return companyID;
	}
	
	public void setCompanyID(String companyID){
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

	public String getTrendOfRequest() {
		return trendOfRequest;
	}

	public void setTrendOfRequest(String trendOfRequest) {
		this.trendOfRequest = trendOfRequest;
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
