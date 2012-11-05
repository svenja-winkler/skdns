package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class OwnCompany extends Company implements Serializable {

	private static final long serialVersionUID = 1L;
	private double fixedCosts;
	private double variableCosts;
	private Machines machines;
	private int salaryStaff;
	private int numberOfStaff;

	public OwnCompany() {
	}

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
