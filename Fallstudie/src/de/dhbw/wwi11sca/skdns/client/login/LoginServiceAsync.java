package de.dhbw.wwi11sca.skdns.client.login;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * LoginServiceAsync ist ein Interface f�r die Kommunikation der LoginSimulation mit der LoginServiceImpl im Server.
 *
 */
import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	void loginServer(String name, AsyncCallback<String> callback);

	void checkLogin(User user, AsyncCallback<Void> callback);

	void forgotPassword(User user, AsyncCallback<Void> callback);

	void checkAdmin(User userOnline, AsyncCallback<Void> callback);
	
	
}
