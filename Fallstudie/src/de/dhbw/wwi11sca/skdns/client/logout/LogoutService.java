package de.dhbw.wwi11sca.skdns.client.logout;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * LogoutService ist ein Interface für die Kommunikation der LogoutSimulation mit der LogoutServiceImpl im Server.
 *
 */

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("logout")
public interface LogoutService extends RemoteService {

	void deleteVersions();

}
