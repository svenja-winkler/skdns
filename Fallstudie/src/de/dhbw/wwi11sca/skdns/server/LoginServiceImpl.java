package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die LoginServiceImpl ist die Serverklasse für die LoginSimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */
import java.net.UnknownHostException;
import java.util.List;
import de.dhbw.wwi11sca.skdns.client.DelistedException;
import de.dhbw.wwi11sca.skdns.client.login.LoginService;
import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static String userID;
	boolean control = false;
	private String username;
	private String password;
	private static final long serialVersionUID = -179774088492873807L;
	User userForgottenPassword;

	@Override
	public void checkLogin(User user) throws DelistedException {
		username = (String) user.getUsername().trim();
		password = (String) user.getPassword().trim();

		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");

		List<User> dbUser = ds.createQuery(User.class)
				.filter("username =", username).asList();
		User first = dbUser.get(0);

		
		String dbUsername = (String) first.getUsername();
		String dbPassword = (String) first.getPassword();
		if ((username.equals(dbUsername)) && (password.equals(dbPassword))) {
			AdminServiceImpl.getAdmin().setLoginCount(1);
			setUserID(first.getUserID());
			
			// success
		} else {
			throw new DelistedException("Username oder Passwort falsch/ unbekannt.");
		} // Ende if-else
	} // Ende method checkLogin

	@Override
	public String loginServer(String name) throws IllegalArgumentException {
		return null;
	} // Ende method loginServer

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

	@Override
	public void forgotPassword(User user) throws DelistedException {

		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");

		Query<User> updateQuery = ds.createQuery(User.class).filter(
				"username =", user.getUsername());

		UpdateOperations<User> ops;
		ops = ds.createUpdateOperations(User.class).set("forgottenPassword",
				"true");

		ds.update(updateQuery, ops);

		// success

	} // Ende method forgotPassword

	@Override
	public void checkAdmin(User admin) throws DelistedException {
		
		//Überprüfe ob bereits ein Admin angelegt wurde
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		try{
			List<User> dbAdmin = ds.createQuery(User.class).filter("username = ", "admin").asList();
			User singleAdmin = dbAdmin.get(0);
			
			if (admin.getPassword().equals(singleAdmin.getPassword())){
				//success
			} else {
				throw new DelistedException("Adminpasswort falsch.");
			}
			
		}catch(IndexOutOfBoundsException e){
			User newAdmin = new User("admin", "12345", "admin@skdns.de");
			ds.save(newAdmin);
			
			List<User> dbAdmin = ds.createQuery(User.class).filter("username = ", "admin").asList();
			User singleAdmin = dbAdmin.get(0);
			
			if (admin.getPassword().equals(singleAdmin.getPassword())){
				//success
			} else {
				throw new DelistedException("Adminpasswort falsch.");
			}
			
		}
		
	} // Ende method checkAdmin

	public static String getUserID() {
		return userID;
	} // Ende method getUserID

	public void setUserID(String userID) {
		LoginServiceImpl.userID = userID;
	} // Ende method setUserID


} // Ende class LoginServiceImpl
