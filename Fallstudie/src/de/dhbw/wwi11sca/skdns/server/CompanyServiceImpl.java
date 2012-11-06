package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die CompanyServiceImpl ist die Serverklasse für die CompanySimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */

import java.util.List;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.dhbw.wwi11sca.skdns.client.company.CompanyService;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

@SuppressWarnings("serial")
public class CompanyServiceImpl extends RemoteServiceServlet implements
		CompanyService {

	/**
	 * getCompany Ermittelt alle Konkurrenzunternehmen, deren UserID-Feld mit
	 * der UserID des eingeloggten Users übereinstimmt
	 */
	public final List<Company> getCompany() {

		return DataManager.getDatastore().createQuery(Company.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();
	} // Ende method getCompany

	/**
	 * getOwnCompany Ermittelt das eigene Unternehmen, dessen UserID-Feld mit
	 * der UserID des eingeloggten Users übereinstimmt
	 */
	public final OwnCompany getOwnCompany() {
		List<OwnCompany> dbOwnCompany = DataManager.getDatastore()
				.createQuery(OwnCompany.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();
		OwnCompany single = dbOwnCompany.get(0);
		return single;
	} // Ende method getOwnCompany

	/**
	 * addOwnCompany aktualisiert bei Änderungen von Daten auf der Oberfläche
	 * das eigene Unternehmen, dessen UserID-Feld mit der UserID des
	 * eingeloggten Users übereinstimmt
	 */
	public final void addOwnCompany(final OwnCompany ownCompany) {
		// Ermittelt das zu aktualisierende eigene Unternehmen
		Query<OwnCompany> updateQuery = DataManager.getDatastore()
				.createQuery(OwnCompany.class).field("userID")
				.equal(LoginServiceImpl.getUserID());

		// aktualisiert das ermittelte Unternehmen
		UpdateOperations<OwnCompany> ops;
		ops = DataManager.getDatastore()
				.createUpdateOperations(OwnCompany.class)
				.set("tradeName", ownCompany.getTradeName())
				.set("topLine", ownCompany.getTopLine())
				.set("marketShare", ownCompany.getMarketShare())
				.set("fixedCosts", ownCompany.getFixedCosts())
				.set("numberOfStaff", ownCompany.getNumberOfStaff())
				.set("salaryStaff", ownCompany.getSalaryStaff())
				.set("product", ownCompany.getProduct())
				.set("machines", ownCompany.getMachines());

		DataManager.getDatastore().update(updateQuery, ops);
	} // Ende method addOwnCompany

	/**
	 * addCompany aktualisiert bei Änderungen von Daten auf der Oberfläche ein
	 * Konkurrenzunternehmen, dessen User-ID-Feld mit der UserID des
	 * eingeloggten Users und der CompanyID, des in der DB befindlichen
	 * Unternehmens übereinstimmt
	 */
	public final void addCompany(final Company company) {
		// Ermittelt das zu aktualisierende Unternehmen
		Query<Company> updateQuery = DataManager.getDatastore()
				.createQuery(Company.class)
				.filter("userID = ", LoginServiceImpl.getUserID())
				.filter("companyID = ", company.getCompanyID());
		// aktualisiert das ermittelte Unternehmen

		UpdateOperations<Company> ops;
		ops = DataManager.getDatastore().createUpdateOperations(Company.class)
				.set("topLine", company.getTopLine())
				.set("marketShare", company.getMarketShare())
				.set("product", company.getProduct());

		DataManager.getDatastore().update(updateQuery, ops);
	} // Ende method addCompany

} // Ende class CompanyServiceImpl
