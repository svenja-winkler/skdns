package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die HomeServiceImpl ist die Serverklasse für die HomeSimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */

import java.util.List;
import de.dhbw.wwi11sca.skdns.client.home.HomeService;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class HomeServiceImpl extends RemoteServiceServlet implements
		HomeService {
	/**
	 * getCompany ermittelt alle Unternehmen, die unter der UserID des
	 * eingeloggten Users in der DB vorhanden sind
	 */
	public final List<Company> getCompany() {
		// Ermittelt die Konkurrenzunternehmen
		List<Company> dbCompany = DataManager.getDatastore()
				.createQuery(Company.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();

		// Ermittelt das eigene Unternehmen
		List<OwnCompany> dbOwnCompany = DataManager.getDatastore()
				.createQuery(OwnCompany.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();

		OwnCompany single = dbOwnCompany.get(0);
		// das eigene Unternehmen wird an den Anfang der Unternehmensliste
		// gesetzt
		dbCompany.add(0, single);

		return dbCompany;
	} // Ende method getCompany

} // Ende class HomeServiceImpl
