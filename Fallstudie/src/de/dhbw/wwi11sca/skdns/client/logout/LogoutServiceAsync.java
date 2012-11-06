package de.dhbw.wwi11sca.skdns.client.logout;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * LogoutServiceAsync ist ein Interface für die Kommunikation 
 * der LogoutSimulation mit der LogoutServiceImpl im Server.
 *
 */

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogoutServiceAsync {

	void deleteVersions(AsyncCallback<Void> callback);

}
