package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1889396499053755175L;
	private static Admin admin = new Admin();

	@Override
	public List<User> getUser() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<User> dbUser = ds.createQuery(User.class).asList();
		return dbUser;
	} // Enge methode getUser

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
	public void saveUser(User newUser) {
		newUser.setUserID(newUser.getUsername());		
		
		OwnCompany ownCompany = new OwnCompany();
		Product ownProduct = new Product();
		Machines ownMachines = new Machines();
		ownMachines.setUserID(newUser.getUsername());
		ownCompany.setMachines(ownMachines);
		ownProduct.setUserID(newUser.getUsername());
		ownCompany.setProduct(ownProduct);
		ownCompany.setTradeName("Eigenes Unternehmen");
		ownCompany.setUserID(newUser.getUsername());
		ownCompany.setCompanyID("ownCom");
		
		Company company1 = new Company();
		company1.setTradeName("Competitior");
		company1.setUserID(newUser.getUsername());
		company1.setCompanyID("1");
		Product productCom1 = new Product();
		productCom1.setUserID(newUser.getUsername());
		company1.setProduct(productCom1);
		
		Company company2 = new Company();
		company2.setTradeName("Competitior");
		company2.setUserID(newUser.getUsername());
		company2.setCompanyID("2");
		Product productCom2 = new Product();
		productCom2.setUserID(newUser.getUsername());
		company2.setProduct(productCom2);
		
		Company company3 = new Company();
		company3.setTradeName("Competitior");
		company3.setUserID(newUser.getUsername());
		company3.setCompanyID("3");
		Product productCom3 = new Product();
		productCom3.setUserID(newUser.getUsername());
		company3.setProduct(productCom3);
		
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		ds.save(newUser);
		ds.save(ownCompany);
		ds.save(company1);
		ds.save(company2);
		ds.save(company3);
		
		
		
	} // Ende method saveUser

	@Override
	public void deleteUser(String deleteUser) {		
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		ds.delete(ds.createQuery(User.class).filter("userID = ", deleteUser));
		ds.delete(ds.createQuery(OwnCompany.class).filter("userID = ", deleteUser));
		ds.delete(ds.createQuery(Company.class).filter("userID = ", deleteUser));
		ds.delete(ds.createQuery(Product.class).filter("userID = ", deleteUser));
		ds.delete(ds.createQuery(Machines.class).filter("userID = ", deleteUser));
		ds.delete(ds.createQuery(SimulationVersion.class).filter("userID = ", deleteUser));
	} // Ende method deleteUser

	@Override
	public Admin getStats() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<User> allUser = ds.createQuery(User.class).filter("username <>", "admin").asList();
		admin.setExistingUserCount(allUser.size());
		
		return getAdmin();
	} // Ende method getStats

	public static Admin getAdmin() {
		return admin;
	} // Ende method getAdmin

	public void setAdmin(Admin admin) {
		AdminServiceImpl.admin = admin;
	} // Ende method setAdmin

	@Override
	public void updateTable(User user) {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		Query<User> updateQuery = ds.createQuery(User.class).field("userID").equal(user.getUserID());
		// ds.createQuery(EigenesUnternehmen.class);
		UpdateOperations<User> ops;
		ops = ds.createUpdateOperations(User.class)
				.set("password", user.getPassword())
				.set("forgottenPassword", user.isForgottenPassword());
								
		ds.update(updateQuery,ops);
	}

} // Ende class AdminServiceImpl
