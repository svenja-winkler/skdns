package de.dhbw.wwi11sca.skdns.client.logout;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * LoginService ist ein Interface für die Kommunikation der LoginSimulation mit der LoginServiceImpl im Server.
 *
 */

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("logout")
public interface LogoutService extends RemoteService {


	void deleteVersions();
	
}
