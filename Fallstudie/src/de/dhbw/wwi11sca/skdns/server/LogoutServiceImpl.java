package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die LogoutServiceImpl ist die Serverklasse für die LogoutSimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.dhbw.wwi11sca.skdns.client.logout.LogoutService;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

public class LogoutServiceImpl extends RemoteServiceServlet implements
		LogoutService {

	//Constants
	private static final int THREE = 10;
	
	private static final long serialVersionUID = -1627396366340846030L;

	/**
	 * deleteVersions entfernt alle SimulationVersionen des sich ausloggenden
	 * Users, abgesehen der letzten drei
	 */
	public final void deleteVersions() {
		// überprüft, ob die UserID, die des Admins ist
		// ist dies der Fall können keine Versionen gelöscht werden
		if (LoginServiceImpl.getUserID() != null) {
			// ermittelt alle SimulationVersions in der DB, die zu der UserID
			// passen
			List<SimulationVersion> versions = DataManager.getDatastore()
					.createQuery(SimulationVersion.class)
					.filter("userID =", LoginServiceImpl.getUserID()).asList();

			// überprüft, ob mehr als drei SimulationVersions in der DB
			// vorhanden sind
			if (versions.size() > THREE) {
				// löschen der SimulationVersions, abgesehen der letzten drei
				DataManager.getDatastore().delete(
						DataManager
								.getDatastore()
								.createQuery(SimulationVersion.class)
								.filter("userID = ",
										LoginServiceImpl.getUserID()));
				versions.get(versions.size() - 1);
				for (int i = versions.size(); i > versions.size()
						- THREE; i--) {
					DataManager.getDatastore().save(versions.get(i));
				} // Ende if-Statement(version.size() >3)
			} // Ende if-Statement (LoginServiceImpl.getUserID() != null)
		} // Ende method deleteVersions
	} // Ende class LogoutServiceImpl
} // Ende class LogoutServiceImpl
