package de.dhbw.wwi11sca.skdns.client.logout;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * LoginServiceAsync ist ein Interface f�r die Kommunikation der LoginSimulation mit der LoginServiceImpl im Server.
 *
 */

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogoutServiceAsync {


	void deleteVersions(AsyncCallback<Void> callback);
	
}
