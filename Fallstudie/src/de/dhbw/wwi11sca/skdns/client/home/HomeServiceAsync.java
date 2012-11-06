package de.dhbw.wwi11sca.skdns.client.home;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * HomeServiceAsync ist ein Interface für die Kommunikation der HomeSimulation mit der HomeServiceImpl im Server.
 *
 */
import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.dhbw.wwi11sca.skdns.shared.Company;

public interface HomeServiceAsync {

	void getCompany(AsyncCallback<List<Company>> callback);
}
