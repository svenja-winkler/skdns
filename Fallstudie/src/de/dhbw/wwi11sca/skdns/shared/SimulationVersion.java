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

	public SimulationVersion(int simulationYear, int simulationVersion) {
		this.setSimulationYear(simulationYear);
		this.setVersion(simulationVersion);

	}

	// Getter-Setter-Methoden

	public int getPersonal() {
		return personal;
	}

	public void setPersonal(int personal) {
		this.personal = personal;
	}

	public int getMachineValue() {
		return machineValue;
	}

	public void setMachineValue(int machineValue) {
		this.machineValue = machineValue;
	}

	public int getMachineCapacity() {
		return machineCapacity;
	}

	public void setMachineCapacity(int machineCapacity) {
		this.machineCapacity = machineCapacity;
	}

	public int getMachineStaff() {
		return machineStaff;
	}

	public void setMachineStaff(int machineStaff) {
		this.machineStaff = machineStaff;
	}

	public int getMarketing() {
		return marketing;
	}

	public void setMarketing(int marketing) {
		this.marketing = marketing;
	}

	public int getSimulationYear() {
		return simulationYear;
	}

	public void setSimulationYear(int simulationYear) {
		this.simulationYear = simulationYear;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double double1) {
		this.price = double1;
	}

	public int getMarketIncrease() {
		return marketIncrease;
	}

	public void setMarketIncrease(int marketIncrease) {
		this.marketIncrease = marketIncrease;
	}

	public OwnCompany getOwnCompany() {
		return ownCompany;
	}

	public void setOwnCompany(OwnCompany ownCompany) {
		this.ownCompany = ownCompany;
	}

	public Company getCompany1() {
		return company1;
	}

	public void setCompany1(Company company1) {
		this.company1 = company1;
	}

	public Company getCompany2() {
		return company2;
	}

	public void setCompany2(Company company2) {
		this.company2 = company2;
	}

	public Company getCompany3() {
		return company3;
	}

	public void setCompany3(Company company3) {
		this.company3 = company3;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public boolean isUnusedMachineCapacity() {
		return unusedMachineCapacity;
	}

	public void setUnusedMachineCapacity(boolean unusedMachineCapacity) {
		this.unusedMachineCapacity = unusedMachineCapacity;
	}

	public int getNecessaryPersonal() {
		return necessaryPersonal;
	}

	public void setNecessaryPersonal(int necessaryPersonal) {
		this.necessaryPersonal = necessaryPersonal;
	}

}
