package de.dhbw.wwi11sca.skdns.client.simulation;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * SimulationServiceAsync ist ein Interface für die Kommunikation der Simulation mit der SimulationServiceImpl im Server.
 *
 */
import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimulationServiceAsync {

	void getCompany(AsyncCallback<List<Company>> callback);

	void createSimulationCallback(SimulationVersion version,
			AsyncCallback<SimulationVersion> callback);

}
