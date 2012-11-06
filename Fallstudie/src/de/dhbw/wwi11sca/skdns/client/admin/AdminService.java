package de.dhbw.wwi11sca.skdns.client.admin;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * AdminService ist ein Interface für die Kommunikation der AdminSimulation mit der AdminnServiceImpl im Server.
 *
 */
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.Admin;
import de.dhbw.wwi11sca.skdns.shared.User;

@RemoteServiceRelativePath("admin")
public interface AdminService extends RemoteService {

	List<User> getUser();

	void updateTable(User user);

	void saveUser(User newUser);

	void deleteUser(String deleteUser);

	Admin getStats();

}
