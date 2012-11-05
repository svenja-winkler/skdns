package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import de.dhbw.wwi11sca.skdns.client.logout.LogoutService;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;


public class LogoutServiceImpl extends RemoteServiceServlet implements
		LogoutService {

	private static final long serialVersionUID = -1627396366340846030L;

	@Override
	public void deleteVersions() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<SimulationVersion> versions = ds
				.createQuery(SimulationVersion.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();


		if(versions.size() > 3)
			{
			ds.delete(ds.createQuery(SimulationVersion.class).filter("userID = ",
					LoginServiceImpl.getUserID()));
			versions.get(versions.size()-1);
			for (int i = versions.size(); i > versions.size() - 3; i--) {
				ds.save(versions.get(i));
			}
			}


		
	}

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

} // Ende class LogoutServiceImpl
