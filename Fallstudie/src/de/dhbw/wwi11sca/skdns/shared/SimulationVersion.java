package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten eines Spieldurchlaufes.
 * 
 */
import java.io.Serializable;

public class SimulationVersion implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID = new String();
	
	private int personal;
	private int machineValue;
	private int machineCapacity;
	private int machineStaff;
	private int marketing;
	private Double price;

	private int simulationYear;
	private int version;

	private OwnCompany ownCompany;
	private Company company1;
	private Company company2;
	private Company company3;

	private int marketIncrease;
	private boolean unusedMachineCapacity = false;
	private int necessaryPersonal;

	// Konstruktoren
	public SimulationVersion() {

	}

	public SimulationVersion(final int pSimulationYear, 
			final int pSimulationVersion) {
		this.setSimulationYear(pSimulationYear);
		this.setVersion(pSimulationVersion);

	}

	// Getter-Setter-Methoden

	public final int getPersonal() {
		return personal;
	}

	public final void setPersonal(final int pPersonal) {
		this.personal = pPersonal;
	}

	public final int getMachineValue() {
		return machineValue;
	}

	public final void setMachineValue(final int pMachineValue) {
		this.machineValue = pMachineValue;
	}

	public final int getMachineCapacity() {
		return machineCapacity;
	}

	public final void setMachineCapacity(final int pMachineCapacity) {
		this.machineCapacity = pMachineCapacity;
	}

	public final int getMachineStaff() {
		return machineStaff;
	}

	public final void setMachineStaff(final int pMachineStaff) {
		this.machineStaff = pMachineStaff;
	}

	public final int getMarketing() {
		return marketing;
	}

	public final void setMarketing(final int pMarketing) {
		this.marketing = pMarketing;
	}

	public final int getSimulationYear() {
		return simulationYear;
	}

	public final void setSimulationYear(final int pSimulationYear) {
		this.simulationYear = pSimulationYear;
	}

	public final int getVersion() {
		return version;
	}

	public final void setVersion(final int pVersion) {
		this.version = pVersion;
	}

	public final Double getPrice() {
		return price;
	}

	public final void setPrice(final Double pDouble1) {
		this.price = pDouble1;
	}

	public final int getMarketIncrease() {
		return marketIncrease;
	}

	public final void setMarketIncrease(final int pMarketIncrease) {
		this.marketIncrease = pMarketIncrease;
	}

	public final OwnCompany getOwnCompany() {
		return ownCompany;
	}

	public final void setOwnCompany(final OwnCompany pOwnCompany) {
		this.ownCompany = pOwnCompany;
	}

	public final Company getCompany1() {
		return company1;
	}

	public final void setCompany1(final Company pCompany1) {
		this.company1 = pCompany1;
	}

	public final Company getCompany2() {
		return company2;
	}

	public final void setCompany2(final Company pCompany2) {
		this.company2 = pCompany2;
	}

	public final Company getCompany3() {
		return company3;
	}

	public final void setCompany3(final Company pCompany3) {
		this.company3 = pCompany3;
	}

	public final String getUserID() {
		return userID;
	}

	public final void setUserID(final String pUserID) {
		this.userID = pUserID;
	}

	public final boolean isUnusedMachineCapacity() {
		return unusedMachineCapacity;
	}

	public final void setUnusedMachineCapacity(
			final boolean pUnusedMachineCapacity) {
		this.unusedMachineCapacity = pUnusedMachineCapacity;
	}

	public final int getNecessaryPersonal() {
		return necessaryPersonal;
	}

	public final void setNecessaryPersonal(final int pNecessaryPersonal) {
		this.necessaryPersonal = pNecessaryPersonal;
	}

}
