package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public final class DataManager {
	
	public static final int PORT = 27017;
	
	private DataManager() {
		
	}
	
	public static Datastore getDatastore() {
		return new Morphia().createDatastore(getMongo(), "skdns");
	}

	private static Mongo getMongo() {
		Mongo m = null;
		try {
			m = new Mongo("localhost", PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return m;
	} // Ende method getMongo

}
