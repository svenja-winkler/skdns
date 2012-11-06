package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die LoginServiceImpl ist die Serverklasse für die LoginSimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */

import java.util.List;
import de.dhbw.wwi11sca.skdns.client.DelistedException;
import de.dhbw.wwi11sca.skdns.client.login.LoginService;
import de.dhbw.wwi11sca.skdns.shared.User;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = -179774088492873807L;
	private static String userID;
	private String username;
	private String password;
	private String adminpassword = new String("12345");
	private String adminEMail = new String("admin@skdns.de");

	/**
	 * checkLogin kontrolliert die eingegebenen Userdaten
	 */
	public final void checkLogin(final User user) throws DelistedException {
		username = (String) user.getUsername().trim();
		password = (String) user.getPassword().trim();

		// Ermittelt, ob der Username in der DB vorhanden ist
		List<User> dbUser = DataManager.getDatastore().createQuery(User.class)
				.filter("username =", username).asList();

		User first = dbUser.get(0);

		String dbUsername = (String) first.getUsername();
		String dbPassword = (String) first.getPassword();

		// Ermittelt, ob Username und Passwort mit den Daten der DB
		// übereinstimmen
		if ((username.equals(dbUsername)) && (password.equals(dbPassword))) {
			AdminServiceImpl.getAdmin().setLoginCount(1);
			setUserID(first.getUserID());

			// success
		} else {
			throw new DelistedException(
					"Username oder Passwort falsch/ unbekannt.");
		} // Ende if-else
	} // Ende method checkLogin

	/**
	 * ForgotPassword meldet dem Admin, dass der User sein Password vergessen
	 * hat, indem er forgottenPassword auf true setzt
	 */
	public final void forgotPassword(final User user) throws DelistedException {

		Query<User> updateQuery = DataManager.getDatastore()
				.createQuery(User.class)
				.filter("username =", user.getUsername());

		UpdateOperations<User> ops;
		ops = DataManager.getDatastore().createUpdateOperations(User.class)
				.set("forgottenPassword", "true");

		DataManager.getDatastore().update(updateQuery, ops);

		// success

	} // Ende method forgotPassword

	/**
	 * checkAdmin überprüft ob das Password des Admins mti dem Password der DB
	 * übereinstimmt
	 */
	public final void checkAdmin(final User admin) throws DelistedException {

		// überprüft, ob bereits ein Admin in der DB vorhanden ist, ist dies
		// nicht der Fall wird ein Admin angelegt
		try {
			// Admin ist vorhanden
			List<User> dbAdmin = DataManager.getDatastore()
					.createQuery(User.class).filter("username = ", "admin")
					.asList();
			User singleAdmin = dbAdmin.get(0);
			// überprüft, ob das Passwort mit dem aus der DB übereinstimmt
			if (!(admin.getPassword().equals(singleAdmin.getPassword()))) {
				throw new DelistedException("Adminpasswort falsch.");
			} //ende if-else

		} catch (IndexOutOfBoundsException e) {
			// Admin nicht vorhanden
			// Erzeugen eines neuen Admins
			User newAdmin = new User("admin", adminpassword, adminEMail);
			DataManager.getDatastore().save(newAdmin);

			// überprüft, ob das eingegebene Passwort mit dem neu erzeugten
			// Passwort übereinstimmt
			List<User> dbAdmin = DataManager.getDatastore()
					.createQuery(User.class).filter("username = ", "admin")
					.asList();
			User singleAdmin = dbAdmin.get(0);

			if (!(admin.getPassword().equals(singleAdmin.getPassword()))) {
				throw new DelistedException("Adminpasswort falsch.");
			} // Ende if-else
		} // Ende try-catch
	} // Ende method checkAdmin

	// Getter-Setter-Methoden für die UserID
	public static String getUserID() {
		return userID;
	} // Ende method getUserID

	public final void setUserID(final String pUserID) {
		LoginServiceImpl.userID = pUserID;
	} // Ende method setUserID

} // Ende class LoginServiceImpl
