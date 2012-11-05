package de.dhbw.wwi11sca.skdns.client.home;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * HomeService ist ein Interface für die Kommunikation der HomeSimulation mit der HomeServiceImpl im Server.
 *
 */

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

@RemoteServiceRelativePath("home")
public interface HomeService extends RemoteService {

	String loginServer(String name) throws IllegalArgumentException;

	List<Company> getCompany();

	

}
