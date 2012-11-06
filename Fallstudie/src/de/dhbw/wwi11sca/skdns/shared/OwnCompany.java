package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten des eigenen Unternehmens.
 * 
 */
import java.io.Serializable;

public class OwnCompany extends Company implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private double fixedCosts;
	private double variableCosts;
	private Machines machines;
	private int salaryStaff;
	private int numberOfStaff;

	// Konstruktoren
	public OwnCompany() {
	}

	public OwnCompany(final String pUserID, final String pCompanyID, 
			final int pTopLine, final double pMarketShare, 
			final Product pProduct, final String pTradeName,
			final double pFixedCosts, final double pVariableCosts,
			final Machines pMachines, final int pSalaryStaff,
			final int pNumberOfStaff) {
		
		setUserID(pUserID);
		setCompanyID(pCompanyID);
		setTopLine(pTopLine);
		setMarketShare(pMarketShare);
		setProduct(pProduct);
		setTradeName(pTradeName);
		this.fixedCosts = pFixedCosts;
		this.variableCosts = pVariableCosts;
		this.machines = pMachines;
		this.salaryStaff = pSalaryStaff;
		this.numberOfStaff = pNumberOfStaff;

	}

	// Getter-Setter-Methoden
	public final double getFixedCosts() {
		return fixedCosts;
	}

	public final void setFixedCosts(final double pFixedCosts) {
		this.fixedCosts = pFixedCosts;
	}

	public final double getVariableCosts() {
		return variableCosts;
	}

	public final void setVariableCosts(final double pVariableCosts) {
		this.variableCosts = pVariableCosts;
	}

	public final Machines getMachines() {
		return machines;
	}

	public final void setMachines(final Machines pMachines) {
		this.machines = pMachines;
	}

	public final int getSalaryStaff() {
		return salaryStaff;
	}

	public final void setSalaryStaff(final int pSalaryStaff) {
		this.salaryStaff = pSalaryStaff;
	}

	public final int getNumberOfStaff() {
		return numberOfStaff;
	}

	public final void setNumberOfStaff(final int pNumberOfStaff) {
		this.numberOfStaff = pNumberOfStaff;
	}

}
