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

	public OwnCompany(String userID, String companyID, int topLine,
			double marketShare, Product product, String tradeName,
			double fixedCosts, double variableCosts, Machines machines,
			int salaryStaff, int numberOfStaff) {
		setUserID(userID);
		setCompanyID(companyID);
		setTopLine(topLine);
		setMarketShare(marketShare);
		setProduct(product);
		setTradeName(tradeName);
		this.fixedCosts = fixedCosts;
		this.variableCosts = variableCosts;
		this.machines = machines;
		this.salaryStaff = salaryStaff;
		this.numberOfStaff = numberOfStaff;

	}

	// Getter-Setter-Methoden
	public double getFixedCosts() {
		return fixedCosts;
	}

	public void setFixedCosts(double fixedCosts) {
		this.fixedCosts = fixedCosts;
	}

	public double getVariableCosts() {
		return variableCosts;
	}

	public void setVariableCosts(double variableCosts) {
		this.variableCosts = variableCosts;
	}

	public Machines getMachines() {
		return machines;
	}

	public void setMachines(Machines machines) {
		this.machines = machines;
	}

	public int getSalaryStaff() {
		return salaryStaff;
	}

	public void setSalaryStaff(int salaryStaff) {
		this.salaryStaff = salaryStaff;
	}

	public int getNumberOfStaff() {
		return numberOfStaff;
	}

	public void setNumberOfStaff(int numberOfStaff) {
		this.numberOfStaff = numberOfStaff;
	}

}
