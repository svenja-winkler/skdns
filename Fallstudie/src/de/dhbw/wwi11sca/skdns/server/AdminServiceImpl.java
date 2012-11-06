package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die AdminServiceImpl ist die Serverklasse für die AdminSimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */

import java.util.List;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.dhbw.wwi11sca.skdns.client.admin.AdminService;
import de.dhbw.wwi11sca.skdns.shared.Admin;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.Machines;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.Product;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;
import de.dhbw.wwi11sca.skdns.shared.User;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = -1889396499053755175L;

	private static Admin admin = new Admin();
	private static final double PRODUCTPRICE = 12.34;
	private static final int MACHINESCAPACITY = 100046;
	private static final int MACHINESSERVICELIFE = 10;
	private static final int MACHINESSTAFF = 10;
	private static final double MACHINESACCOUNTINGVALUE = 1234.56;
	private static final double OWNCOMMARKETSHARE = 50.05;
	private static final double COMMARKETSHARE = 49.95;
	private static final int TOPLINE = 1234567;
	private static final double FIXEDCOSTS = 12345;
	private static final double VARIABLECOSTS = 4.56;
	private static final int SALARYSTAFF = 12345;
	private static final int NUMBEROFSTAFF = 15;
	/**
	 * getUser holt alle User aus der DB
	 */
	public final List<User> getUser() {
		return DataManager.getDatastore().createQuery(User.class).asList();

	} // Ende method getUser

	/**
	 * saveUser speichert einen neu erzeugten User in der DB
	 */
	public final void saveUser(final User newUser) {
		newUser.setUserID(newUser.getUsername());

		// eigenes Unternehmen des neuen Users erzeugen
		// und mit vordefinierten Werten befüllen
		Product ownProduct = new Product();
		ownProduct.setPrice(PRODUCTPRICE);
		ownProduct.setUserID(newUser.getUsername());
		Machines ownMachine = new Machines(MACHINESCAPACITY,
				MACHINESSERVICELIFE, MACHINESSTAFF, MACHINESACCOUNTINGVALUE);
		OwnCompany ownCompany = new OwnCompany(newUser.getUsername(), "ownCom",
				TOPLINE, OWNCOMMARKETSHARE, ownProduct, "Eigenes Unternehmen",
				FIXEDCOSTS, VARIABLECOSTS, ownMachine, SALARYSTAFF,
				NUMBEROFSTAFF);

		// Konkurrenzunternehmen 1 erzeugen
		// und mit vordefinierten Werten befüllen
		Product productCom1 = new Product();
		productCom1.setPrice(PRODUCTPRICE);
		productCom1.setUserID(newUser.getUsername());
		Company company1 = new Company(newUser.getUsername(), "1",
				"Konkurrent", TOPLINE, COMMARKETSHARE, productCom1);

		// Konkurrenzunternehmen 2 erzeugen
		Product productCom2 = new Product();
		productCom2.setUserID(newUser.getUsername());
		Company company2 = new Company(newUser.getUsername(), "2",
				"Konkurrent", productCom2);

		// Konkurrenzunternehmen 3 erzeugen
		Product productCom3 = new Product();
		productCom3.setUserID(newUser.getUsername());
		Company company3 = new Company(newUser.getUsername(), "3",
				"Konkurrent", productCom3);

		// Elemente des neuen Users in der DB speichern
		DataManager.getDatastore().save(newUser);
		DataManager.getDatastore().save(ownCompany);
		DataManager.getDatastore().save(company1);
		DataManager.getDatastore().save(company2);
		DataManager.getDatastore().save(company3);

	} // Ende method saveUser

	/**
	 * deleteUser löscht einen angegebenen User
	 */
	public final void deleteUser(final String deleteUser) {

		// Löscht den User aus der DB
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(User.class)
						.filter("userID = ", deleteUser));
		// Löscht das eigene Unternehmen des Users aus der DB
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(OwnCompany.class)
						.filter("userID = ", deleteUser));
		// Löscht alle Konkurrenzunternehmen des Users aus der DB
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(Company.class)
						.filter("userID = ", deleteUser));
		// Löscht alle Produkte des Users (und der Konkurrenzunternehmen) aus
		// der DB
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(Product.class)
						.filter("userID = ", deleteUser));
		// Löscht alle Maschinen des Users aus der DB
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(Machines.class)
						.filter("userID = ", deleteUser));
		// Löscht alle vom User erstellten Simulationsversionen aus der DB
		DataManager.getDatastore().delete(
				DataManager.getDatastore().createQuery(SimulationVersion.class)
						.filter("userID = ", deleteUser));
	} // Ende method deleteUser

	/**
	 * getStats ermittelt die Statistiken, die dem Admin angezeigt werden
	 */
	public final Admin getStats() {
		// Ermittelt die Anzahl aller User in der DB
		List<User> allUser = DataManager.getDatastore().createQuery(User.class)
				.filter("username <>", "admin").asList();
		admin.setExistingUserCount(allUser.size());

		return getAdmin();
	} // Ende method getStats

	/**
	 * updateTable aktualisiert die Usertabelle auf der Oberfläche des Admins
	 */
	public final void updateTable(final User user) {
		// liefert User aus der DB, deren ID mit der von user übereinstimmt
		Query<User> updateQuery = DataManager.getDatastore()
				.createQuery(User.class).field("userID")
				.equal(user.getUserID());

		// Passwort des Users wird aktualisiert
		UpdateOperations<User> ops;
		ops = DataManager.getDatastore().createUpdateOperations(User.class)
				.set("password", user.getPassword())
				.set("forgottenPassword", user.isForgottenPassword());

		DataManager.getDatastore().update(updateQuery, ops);
	} // Ende method updateTable

	/**
	 * Getter-Setter-Methoden für das Objekt admin
	 */
	public static Admin getAdmin() {
		return admin;
	} // Ende method getAdmin

	public final void setAdmin(final Admin pAdmin) {
		AdminServiceImpl.admin = pAdmin;
	} // Ende method setAdmin
} // Ende class AdminServiceImpl
