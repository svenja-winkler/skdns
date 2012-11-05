package de.dhbw.wwi11sca.skdns.client.company;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * CompanyService ist ein Interface für die Kommunikation der 
 * CompanySimulation mit der CompanyServiceImpl im Server.
 *
 */
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

@RemoteServiceRelativePath("company")
public interface CompanyService extends RemoteService {

	List<Company> getCompany();

	OwnCompany getOwnCompany();

	void addOwnCompany(OwnCompany ownCompany);

	void addCompany(Company company);
}
