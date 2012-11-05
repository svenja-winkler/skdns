package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;
import java.util.List;
import de.dhbw.wwi11sca.skdns.client.simulation.SimulationService;
import de.dhbw.wwi11sca.skdns.server.MarketSimulation;
import de.dhbw.wwi11sca.skdns.shared.*;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SimulationServiceImpl extends RemoteServiceServlet implements
		SimulationService {

	@Override
	public List<Company> getCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");

		List<Company> dbCompany = ds.createQuery(Company.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();

		List<OwnCompany> dbOwnCompany = ds.createQuery(OwnCompany.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();

		// Eigenes Unternehmen aus der Datenbank laden und am Anfang der Liste
		// in die Liste aufnehmen
		OwnCompany single = dbOwnCompany.get(0);

		dbCompany.add(0, single);

		return dbCompany;
	} // Ende method getCompany

	@Override
	public SimulationVersion createSimulationCallback(SimulationVersion version) {

		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");

		List<Company> dbCompany = ds.createQuery(Company.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();
		List<OwnCompany> dbOwnCompany = ds.createQuery(OwnCompany.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();

		// TODO die nächsten vier zeilen funktionieren noch nicht
		OwnCompany ownCompany = dbOwnCompany.get(0);
		Company company1 = dbCompany.get(0);
		Company company2 = dbCompany.get(1);
		Company company3 = dbCompany.get(2);

		version.setUserID(LoginServiceImpl.getUserID());
		version.setOwnCompany(ownCompany);
		version.setCompany1(company1);
		version.setCompany2(company2);
		version.setCompany3(company3);

		MarketSimulation marktsim = new MarketSimulation();
		SimulationVersion simversion = new SimulationVersion();

		simversion = marktsim.simulate(version);
		ds.save(simversion);

		return simversion;
	} // Ende method createSimulationCallback

	private static Mongo getMongo() {
		Mongo m = null;
		try {
			m = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return m;
	} // Ende method getMongo
	
} // Ende class SimulationServiceImpl
