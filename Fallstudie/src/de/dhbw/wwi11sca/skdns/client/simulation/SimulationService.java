package de.dhbw.wwi11sca.skdns.client.simulation;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * SimulationService ist ein Interface für die Kommunikation 
 * der Simulation mit der SimulationServiceImpl im Server.
 *
 */
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

@RemoteServiceRelativePath("simulation")
public interface SimulationService extends RemoteService {

	SimulationVersion createSimulationCallback(SimulationVersion version);

	List<Company> getCompany();

	List<Company> getCompany(List<Company> companies);
}
