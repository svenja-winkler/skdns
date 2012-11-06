package de.dhbw.wwi11sca.skdns.client;

import java.io.Serializable;



public class DelistedException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String symbol;
	
	public DelistedException() { }
	public DelistedException(final String pSymbol) {
		this.symbol = pSymbol;
	}
	public final String getSymbol() {
		return this.symbol;
	}
}
