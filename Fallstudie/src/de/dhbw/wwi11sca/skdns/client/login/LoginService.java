package de.dhbw.wwi11sca.skdns.client.login;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * LoginService ist ein Interface für die Kommunikation der LoginSimulation mit der LoginServiceImpl im Server.
 *
 */

import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.dhbw.wwi11sca.skdns.client.DelistedException;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	void checkLogin(User user) throws DelistedException;

	void forgotPassword(User user) throws DelistedException;

	void checkAdmin(User userOnline) throws DelistedException;

}
