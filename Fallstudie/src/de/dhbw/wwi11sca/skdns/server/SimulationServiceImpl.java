package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die SimulationServiceImpl ist die Serverklasse für die Simulation. 
 * Sie greift auf die Datenbank zu.
 *
 */

import java.util.List;
import de.dhbw.wwi11sca.skdns.client.simulation.SimulationService;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SimulationServiceImpl extends RemoteServiceServlet implements
		SimulationService {
	
	//Constants
	private static final int THREE = 3;

	/**
	 * getCompany holt alle Unternehmen aus der DB , deren UserID mit der UserID
	 * des eingeloggten Users übereinstimmt
	 */
	public final List<Company> getCompany() {
		// ermittelt die Konkurrenzunternehmen , deren UserID mit der UserID des
		// eingeloggten Users übereinstimmt
		List<Company> dbCompany = DataManager.getDatastore()
				.createQuery(Company.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();
		// ermittelt das eigene Unternehmen, dessen UserID mit der UserID des
		// eingeloggten Users übereinstimmt
		List<OwnCompany> dbOwnCompany = DataManager.getDatastore()
				.createQuery(OwnCompany.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();

		OwnCompany single = dbOwnCompany.get(0);

		// Das eigene Unternehmen wird an dem Anfang der Liste eingefügt
		dbCompany.add(0, single);

		return dbCompany;
	} // Ende method getCompany

	/**
	 * getCompany(List<<Company> companies speichert die gelieferten
	 * Unternehmen, und liefert sie zurück
	 */
	public final List<Company> getCompany(final List<Company> companies) {
		// löscht die Unternehmen, deren UserID mit der ID des eingeloggten
		// Users übereinstimmen
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(Company.class)
						.filter("userID = ", LoginServiceImpl.getUserID()));
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(OwnCompany.class)
						.filter("userID = ", LoginServiceImpl.getUserID()));

		// speichert die gelieferten Unternehmen in der DB
		for (Company company : companies) {
			DataManager.getDatastore().save(company);
		} // Ende for-Schleife

		// gibt die gelieferten Unternehmen zurück
		List<Company> dbCompany = DataManager.getDatastore()
				.createQuery(Company.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();

		List<OwnCompany> dbOwnCompany = DataManager.getDatastore()
				.createQuery(OwnCompany.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();

		OwnCompany single = dbOwnCompany.get(0);

		// das eigene Unternehmen wird an dem Anfang der Liste eingefügt
		dbCompany.add(0, single);

		return dbCompany;
	} // Ende method getCompany(List<Company> companies)

	/**
	 * createSimulationCallback erstellt eine SimulationVersion, ruft die
	 * Berechnungen in der MarketSimulation auf, speichert die SimulationVersion
	 * und liefert die Ergebnisse an die Oberfläche
	 */
	public final SimulationVersion createSimulationCallback(
			final SimulationVersion version) {
		// Unternehmen aus der DB holen
		List<Company> dbCompany = getCompany();

		// Unternehmen in der SimulationsVersion speichern
		version.setUserID(LoginServiceImpl.getUserID());
		version.setOwnCompany((OwnCompany) dbCompany.get(0));
		version.setCompany1(dbCompany.get(1));
		version.setCompany2(dbCompany.get(2));
		version.setCompany3(dbCompany.get(THREE));

		// Ein neues Objekt erzeugen und aufrufen
		MarketSimulation marktsim = new MarketSimulation();
		SimulationVersion simversion = new SimulationVersion();

		simversion = marktsim.simulate(version);
		DataManager.getDatastore().save(simversion);

		return simversion;
	} // Ende method createSimulationCallback

} // Ende class SimulationServiceImpl
